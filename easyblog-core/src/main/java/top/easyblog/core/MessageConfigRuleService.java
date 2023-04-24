package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.config.QueryMessageConfigsRequest;
import top.easyblog.common.request.message.rule.CreateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRulesRequest;
import top.easyblog.common.request.message.rule.UpdateMessageConfigRuleRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicMessageConfigRuleService;
import top.easyblog.dao.atomic.AtomicMessageConfigService;
import top.easyblog.dao.atomic.AtomicMessageTemplateService;
import top.easyblog.dao.auto.model.MessageConfigRule;
import top.easyblog.dao.auto.model.MessageTemplate;
import top.easyblog.support.context.MessageConfigRuleContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:05
 */
@Slf4j
@Service
public class MessageConfigRuleService {

    @Autowired
    private AtomicMessageConfigRuleService messageConfigRuleService;

    @Autowired
    private AtomicMessageConfigService messageConfigService;

    @Autowired
    private AtomicMessageTemplateService messageTemplateService;

    @Autowired
    private BeanMapper beanMapper;


    public void createConfigRule(CreateMessageConfigRuleRequest request) {
        checkRequestParmaValid(MessageConfigRuleContext.builder()
                .configIds(Arrays.stream(StringUtils.split(request.getConfigIds(), Constants.COMMA)).collect(Collectors.toList()))
                .channel(request.getChannel())
                .templateCode(request.getTemplateCode())
                .checkIfNonNull(Boolean.FALSE)
                .build());
        MessageConfigRule messageConfigRule = beanMapper.buildMessageConfigRule(request);
        messageConfigRuleService.insertOne(messageConfigRule);
    }

    private void checkRequestParmaValid(MessageConfigRuleContext context) {
        if (!(Objects.isNull(context.getChannel()) && context.isCheckIfNonNull())) {
            MessageSendChannel sendChannel = MessageSendChannel.codeOf(context.getChannel());
            if (Objects.isNull(sendChannel)) {
                throw new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_SEND_CHANNEL);
            }
        }

        if (!(CollectionUtils.isEmpty(context.getConfigIds()) && context.isCheckIfNonNull())) {
            List<MessageConfigBean> messageConfigBeans = messageConfigService.queryListByRequest(QueryMessageConfigsRequest.builder()
                    .codes(context.getConfigIds()).deleted(Boolean.FALSE).build());
            if (CollectionUtils.isEmpty(messageConfigBeans) || !Objects.equals(messageConfigBeans.size(), context.getConfigIds().size())) {
                throw new BusinessException(EasyResultCode.ILLEGAL_CONFIG_IDS);
            }
        }
        if (!(StringUtils.isBlank(context.getTemplateCode()) && context.isCheckIfNonNull())) {
            MessageTemplate messageTemplate = messageTemplateService.queryByRequest(QueryMessageTemplateRequest.builder()
                    .templateCode(context.getTemplateCode()).build());
            if (Objects.isNull(messageTemplate)) {
                throw new BusinessException(EasyResultCode.ILLEGAL_TEMPLATE_CODE);
            }
        }
    }

    public void updateConfigRule(String code, UpdateMessageConfigRuleRequest request) {
        MessageConfigRule messageConfigRule = messageConfigRuleService.queryByRequest(QueryMessageConfigRuleRequest.builder()
                .code(code).build());
        if (Objects.isNull(messageConfigRule)) {
            throw new BusinessException(EasyResultCode.MESSAGE_CONFIG_RULE_NOT_FOUND);
        }
        checkRequestParmaValid(MessageConfigRuleContext.builder()
                .configIds(Arrays.stream(StringUtils.split(request.getConfigIds(), Constants.COMMA)).collect(Collectors.toList()))
                .channel(request.getChannel())
                .templateCode(request.getTemplateCode())
                .checkIfNonNull(Boolean.TRUE)
                .build());
        MessageConfigRule newMessageConfigRule = beanMapper.buildMessageConfigRule(request, messageConfigRule.getId());
        messageConfigRuleService.updateByPKSelective(newMessageConfigRule);
    }

    public MessageConfigRuleBean details(QueryMessageConfigRuleRequest request) {
        MessageConfigRule messageConfigRule = messageConfigRuleService.queryByRequest(request);
        if (Objects.isNull(messageConfigRule)) {
            return null;
        }
        return beanMapper.buildMessageConfigRuleBean(messageConfigRule);
    }

    public PageResponse<MessageConfigRuleBean> list(QueryMessageConfigRulesRequest request) {
        long total = messageConfigRuleService.countByRequest(request);
        if (Objects.equals(NumberUtils.LONG_ZERO, total)) {
            return PageResponse.<MessageConfigRuleBean>builder().total(total).offset(request.getOffset()).limit(request.getLimit()).data(Collections.emptyList()).build();
        }
        List<MessageConfigRule> messageConfigRules = messageConfigRuleService.queryListByRequest(request);
        List<MessageConfigRuleBean> messageConfigRuleBeans = messageConfigRules.stream().map(config -> beanMapper.buildMessageConfigRuleBean(config)).collect(Collectors.toList());
        return PageResponse.<MessageConfigRuleBean>builder().total(total).offset(request.getOffset()).limit(request.getLimit()).data(messageConfigRuleBeans).build();
    }
}
