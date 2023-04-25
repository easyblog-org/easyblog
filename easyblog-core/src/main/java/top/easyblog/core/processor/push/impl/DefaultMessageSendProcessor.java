package top.easyblog.core.processor.push.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.management.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.NonNull;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.bean.MessageTemplateBean;
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
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.MessageConfigContext;
import top.easyblog.support.context.MessageSendContext;
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
    private BeanMapper beanMapper;

    @Override
    public boolean doSend(BusinessMessageRecord message) {
        return Optional.ofNullable(message).map(msg -> {
            checkMessageRecordParam(msg);
            MessageConfigContext configContext = initMessageConfig(msg);
            MessageSendContext sendContext = assembleMessage(configContext);
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
        MessageConfigRuleBean messageConfigRule = messageConfigRuleService
                .details(QueryMessageConfigRuleRequest.builder()
                        .businessEvent(msg.getBusinessEvent()).businessModule(msg.getBusinessModule()).build());
        if (Objects.isNull(messageConfigRule)) {
            throw new BusinessException(EasyResultCode.INCORRECT_BUSINESS_EVENT_OR_MODULE);
        }
        if (StringUtils.isBlank(messageConfigRule.getTemplateCode())) {
            throw new IllegalArgumentException("'template_code' can't be empty");
        }
        if (StringUtils.isBlank(messageConfigRule.getConfigIds())) {
            throw new IllegalArgumentException("'config_id' can't be empty");
        }

        List<MessageConfigBean> messageConfigs = messageConfigService.listAll(QueryMessageConfigsRequest.builder()
                .codes(Lists.newArrayList(StringUtils.split(messageConfigRule.getConfigIds(), ","))).build());
        if (CollectionUtils.isEmpty(messageConfigs)) {
            throw new BusinessException(EasyResultCode.INCORRECT_MESSAGE_CONFIGS, "message config can not be empty");
        }

        boolean receiverConfigExists = messageConfigs.stream()
                .filter(config -> StringUtils.equalsIgnoreCase(config.getType(), MessageConfigType.RECEIVER.name()))
                .findAny().isPresent();
        if (!receiverConfigExists) {
            throw new BusinessException(EasyResultCode.INCORRECT_MESSAGE_CONFIGS,
                    " message config receiver must at leaste have one");
        }

        MessageTemplateBean messageTemplate = messageTemplateService.details(QueryMessageTemplateRequest.builder()
                .templateCode(messageConfigRule.getTemplateCode()).build());
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
        MessageSendContext sendContext= MessageSendContext.builder().build();
        Map<String,Message> context.getConfigs().stream().filter(Objects::nonNull).collect(Collectors.toMap(MessageConfigBean::getName, Function.identity(),(x,y)->x));
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
