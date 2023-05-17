package top.easyblog.core.processor.push.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.bean.TemplateValueConfigBean;
import top.easyblog.common.enums.MessageConfigType;
import top.easyblog.common.enums.MessageSendStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.config.QueryMessageConfigsRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.MessageConfigRuleService;
import top.easyblog.core.MessageConfigService;
import top.easyblog.core.MessageTemplateService;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.core.strategy.MessagePushStrategyContext;
import top.easyblog.core.strategy.TemplateParameterParserStrategyContext;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.core.strategy.template.TemplateParameterParseStrategy;
import top.easyblog.dao.atomic.AtomicBusinessMessageRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.BusinessMessageRecordContext;
import top.easyblog.support.context.MessageConfigContext;
import top.easyblog.support.context.MessageParseContext;
import top.easyblog.support.context.MessageSendContext;
import top.easyblog.support.event.MessageSendFailedEvent;
import top.easyblog.support.event.MessageSendSuccessEvent;
import top.easyblog.support.parser.MessageContentParser;
import top.easyblog.support.util.JsonUtils;

@Slf4j
@Component
public class DefaultMessagePushProcessor extends AbstractMessagePushProcessor {

    @Autowired
    private MessageConfigService messageConfigService;

    @Autowired
    private MessageConfigRuleService messageConfigRuleService;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private MessageContentParser messageContentParser;

    @Autowired
    private AtomicBusinessMessageRecordService atomicBusinessMessageRecordService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 过滤消息：消息去重
     * 
     * @param msg
     * @return
     */
    private boolean filter(BusinessMessageRecordContext msg) {
        BusinessMessageRecord record = atomicBusinessMessageRecordService
                .queryByRequest(QueryBusinessMessageRecordRequest.builder().id(msg.getId()).build());
        log.info("[Filter Msg]:{}", JsonUtils.toJSONString(msg));

        return Objects.nonNull(record) && Objects.equals(Boolean.FALSE, record.getDeleted()) &&
                Objects.equals(MessageSendStatus.UNSEND.getCode(), record.getStatus());
    }

    /**
     * 处理消息：将模板按照消息规则进行填充并推送消息
     */
    @Override
    public boolean doSend(BusinessMessageRecordContext message) {
        return Optional.ofNullable(message).filter(this::filter).map(msg -> {
            MessageConfigContext configContext = null;
            MessageSendContext sendContext = null;
            try {
                checkMessageRecordParam(msg);
                configContext = initMessageConfig(msg);
                sendContext = assembleMessage(configContext);
                MessagePushStrategy messageSendStrategy = MessagePushStrategyContext
                        .getMessageSendStrategy(sendContext.getChannel());
                if (Objects.isNull(messageSendStrategy)) {
                    applicationEventPublisher.publishEvent(new MessageSendFailedEvent(configContext,
                            new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_SEND_CHANNEL)));
                    return false;
                }

                messageSendStrategy.push(sendContext);
                applicationEventPublisher.publishEvent(new MessageSendSuccessEvent(sendContext));
            } catch (Exception e) {
                applicationEventPublisher.publishEvent(new MessageSendFailedEvent(configContext, e));
                return false;
            }
            return true;
        }).orElseThrow(() -> new BusinessException(EasyResultCode.MESSAGE_RECORD_CANNOT_NULL));
    }

    /**
     * 初始化消息配置上下文
     *
     * @param msg
     * @return
     */
    private MessageConfigContext initMessageConfig(BusinessMessageRecordContext msg) {
        MessageConfigRuleBean messageConfigRule = messageConfigRuleService.details(QueryMessageConfigRuleRequest
                .builder().businessEvent(msg.getBusinessEvent()).businessModule(msg.getBusinessModule()).build());
        if (Objects.isNull(messageConfigRule)) {
            throw new BusinessException(EasyResultCode.INCORRECT_BUSINESS_EVENT_OR_MODULE);
        }
        if (StringUtils.isBlank(messageConfigRule.getTemplateCode())) {
            throw new IllegalArgumentException("Illagal message config: 'template_code' can't be empty");
        }
        if (StringUtils.isBlank(messageConfigRule.getConfigIds())) {
            throw new IllegalArgumentException("Illagal message config: 'config_ids' can't be empty");
        }

        List<MessageConfigBean> messageConfigs = messageConfigService.listAll(QueryMessageConfigsRequest.builder()
                .codes(Lists.newArrayList(StringUtils.split(messageConfigRule.getConfigIds(), ","))).build());
        if (CollectionUtils.isEmpty(messageConfigs)) {
            throw new BusinessException(EasyResultCode.INCORRECT_MESSAGE_CONFIGS,
                    "Illagal message config: message parmas can not be empty");
        }

        boolean receiverConfigExists = messageConfigs.stream()
                .anyMatch(config -> Objects.nonNull(config)
                        && StringUtils.equalsIgnoreCase(config.getType(), MessageConfigType.RECEIVER.name())
                        && Boolean.FALSE.equals(config.getDeleted()));
        if (!receiverConfigExists) {
            throw new BusinessException(EasyResultCode.INCORRECT_MESSAGE_CONFIGS,
                    "Illagal message config: message param 'receiver' must at least have one");
        }

        MessageTemplateBean messageTemplate = messageTemplateService.details(
                QueryMessageTemplateRequest.builder().templateCode(messageConfigRule.getTemplateCode()).build());
        if (Objects.isNull(messageTemplate)) {
            throw new BusinessException(EasyResultCode.INCORRECT_TEMPLATE_CODE);
        }
        return beanMapper.buildMessageConfigContext(msg, messageConfigRule, messageTemplate, messageConfigs);
    }

    /**
     * 解析&组装消息
     *
     * @param context
     * @return
     */
    private MessageSendContext assembleMessage(MessageConfigContext context) {
        MessageSendContext sendContext = MessageSendContext.builder().build();

        Map<String, Object> messageParams = Maps.newHashMap();
        Map<String, Object> titleParams = Maps.newHashMap();
        List<String> receivers = Lists.newArrayList();
        context.getConfigs().stream().filter(cfg -> Objects.nonNull(cfg) && Boolean.FALSE.equals(cfg.getDeleted()))
                .forEach(config -> {
                    TemplateValueConfigBean templateValueConfigBean = config.getTemplateValueConfig();
                    TemplateParameterParseStrategy parameterParseStrategy = TemplateParameterParserStrategyContext
                            .getMessageSendStrategy(Objects
                                    .requireNonNull(templateValueConfigBean, "Template value config can not be null")
                                    .getType());
                    if (Objects.isNull(parameterParseStrategy)) {
                        throw new BusinessException(EasyResultCode.ILLEGAL_TEMPLATE_VALUE_CONFIG);
                    }

                    //参数解析结果=>name:value
                    Pair<String, Object> templateValuePair = parameterParseStrategy.parse(MessageParseContext.builder()
                            .businessMessage(context.getBusinessMessage())
                            .config(config)
                            .build());
                    if (StringUtils.equalsIgnoreCase(MessageConfigType.RECEIVER.name(), config.getType())) {
                        receivers.add(String.valueOf(templateValuePair.getValue()));
                    } else if (StringUtils.equalsIgnoreCase(MessageConfigType.CONTENT.name(), config.getType())) {
                        messageParams.putIfAbsent(templateValuePair.getKey(), templateValuePair.getValue());
                    } else if (StringUtils.equalsIgnoreCase(MessageConfigType.SUBJECT.name(), config.getType())) {
                        titleParams.putIfAbsent(templateValuePair.getKey(), templateValuePair.getValue());
                    }
                });

        String receiver = StringUtils.join(receivers, ",");
        String messageContent = messageContentParser.parseMessageContent(context.getMsgTemplateContent(),
                messageParams);
        String title = messageContentParser.parseMessageContent(context.getTitle(), titleParams);

        sendContext.setMessageConfigContext(context);
        sendContext.setReceiver(receiver);
        sendContext.setContent(messageContent);
        sendContext.setTitle(title);
        sendContext.setChannel(context.getChannel());
        return sendContext;
    }

    private void checkMessageRecordParam(BusinessMessageRecordContext message) {
        if (StringUtils.isBlank(message.getBusinessMessage())) {
            throw new IllegalArgumentException("'business_message' can't be empty");
        }
        if (StringUtils.isBlank(message.getBusinessEvent())) {
            throw new IllegalArgumentException("'business_event' can't be empty");
        }
        if (StringUtils.isBlank(message.getBusinessModule())) {
            throw new IllegalArgumentException("'business_module' can't be empty");
        }
    }

}
