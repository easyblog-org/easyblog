package top.easyblog.core.processor.push.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.management.Query;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.NonNull;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.bean.TemplateValueConfigBean;
import top.easyblog.common.enums.MessageConfigType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.config.QueryMessageConfigRequest;
import top.easyblog.common.request.message.config.QueryMessageConfigsRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.core.MessageConfigRuleService;
import top.easyblog.core.MessageConfigService;
import top.easyblog.core.MessageTemplateService;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.core.strategy.MessagePushStrategyContext;
import top.easyblog.core.strategy.TemplateParameterParserStrategyContext;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.core.strategy.template.TemplateParameterParseStrategy;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.MessageConfigContext;
import top.easyblog.support.context.MessageSendContext;
import top.easyblog.support.event.MessageSendFailedEvent;
import top.easyblog.support.parser.MessageContentParser;

@Component
public class DefaultMessageSendProcessor extends AbstractMessageSendProcessor {

    @Autowired
    private MessageConfigService messageConfigService;

    @Autowired
    private MessageConfigRuleService messageConfigRuleService;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private MessageContentParser messageContentParser;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    private BeanMapper beanMapper;

    @Override
    public boolean doSend(BusinessMessageRecord message) {
        return Optional.ofNullable(message).map(msg -> {
            checkMessageRecordParam(msg);
            MessageConfigContext configContext = initMessageConfig(msg);
            MessageSendContext sendContext = assembleMessage(configContext);
            MessagePushStrategy messageSendStrategy = MessagePushStrategyContext.getMessageSendStrategy(sendContext.getChannel());
            if (Objects.isNull(messageSendStrategy)) {
                applicationEventPublisher.publishEvent(new MessageSendFailedEvent(configContext));
                return false;
            }

            messageSendStrategy.push(sendContext);
            return true;
        }).orElseThrow(() -> new BusinessException(EasyResultCode.MESSAGE_RECORD_CANNOT_NULL));
    }

    /**
     * 初始化消息配置上下文
     *
     * @param msg
     * @return
     */
    private MessageConfigContext initMessageConfig(BusinessMessageRecord msg) {
        MessageConfigRuleBean messageConfigRule = messageConfigRuleService.details(QueryMessageConfigRuleRequest.builder().businessEvent(msg.getBusinessEvent()).businessModule(msg.getBusinessModule()).build());
        if (Objects.isNull(messageConfigRule)) {
            throw new BusinessException(EasyResultCode.INCORRECT_BUSINESS_EVENT_OR_MODULE);
        }
        if (StringUtils.isBlank(messageConfigRule.getTemplateCode())) {
            throw new IllegalArgumentException("'template_code' can't be empty");
        }
        if (StringUtils.isBlank(messageConfigRule.getConfigIds())) {
            throw new IllegalArgumentException("'config_id' can't be empty");
        }

        List<MessageConfigBean> messageConfigs = messageConfigService.listAll(QueryMessageConfigsRequest.builder().codes(Lists.newArrayList(StringUtils.split(messageConfigRule.getConfigIds(), ","))).build());
        if (CollectionUtils.isEmpty(messageConfigs)) {
            throw new BusinessException(EasyResultCode.INCORRECT_MESSAGE_CONFIGS, "message config can not be empty");
        }

        boolean receiverConfigExists = messageConfigs.stream().anyMatch(config -> Objects.nonNull(config) && StringUtils.equalsIgnoreCase(config.getType(), MessageConfigType.RECEIVER.name()) && Boolean.FALSE.equals(config.getDeleted()));
        if (!receiverConfigExists) {
            throw new BusinessException(EasyResultCode.INCORRECT_MESSAGE_CONFIGS, " message config receiver must at least have one");
        }

        MessageTemplateBean messageTemplate = messageTemplateService.details(QueryMessageTemplateRequest.builder().templateCode(messageConfigRule.getTemplateCode()).build());
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
        context.getConfigs().stream().filter(cfg -> Objects.nonNull(cfg) && Boolean.FALSE.equals(cfg.getDeleted())).forEach(config -> {
            TemplateValueConfigBean templateValueConfigBean = config.getTemplateValueConfigBean();
            TemplateParameterParseStrategy parameterParseStrategy = TemplateParameterParserStrategyContext.getMessageSendStrategy(Objects.requireNonNull(templateValueConfigBean, "Template value config can not be null").getType());
            if (Objects.isNull(parameterParseStrategy)) {
                applicationEventPublisher.publishEvent(new MessageSendFailedEvent(context));
                return;
            }

            Pair<String, Object> templateValue = parameterParseStrategy.parse(config);
            if (StringUtils.equalsIgnoreCase(MessageConfigType.RECEIVER.name(), config.getType())) {
                receivers.add(String.valueOf(templateValue.getValue()));
            } else if (StringUtils.equalsIgnoreCase(MessageConfigType.CONTENT.name(), config.getType())) {
                messageParams.putIfAbsent(templateValue.getKey(), templateValue.getValue());
            } else if (StringUtils.equalsIgnoreCase(MessageConfigType.SUBJECT.name(), config.getType())) {
                titleParams.putIfAbsent(templateValue.getKey(), templateValue.getValue());
            }
        });

        String receiver = StringUtils.join(receivers, ",");
        String messageContent = messageContentParser.parseMessageContent(context.getMsgTemplateContent(), messageParams);
        String title = messageContentParser.parseMessageContent(context.getTitle(), titleParams);

        sendContext.setReceiver(receiver);
        sendContext.setContent(messageContent);
        sendContext.setTitle(title);
        sendContext.setChannel(context.getChannel());
        return sendContext;
    }

    private void checkMessageRecordParam(BusinessMessageRecord message) {
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
