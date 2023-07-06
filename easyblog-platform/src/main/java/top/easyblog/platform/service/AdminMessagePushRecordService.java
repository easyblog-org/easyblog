package top.easyblog.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.BusinessMessageRecordBean;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.enums.MessageTemplateStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordsRequest;
import top.easyblog.common.request.message.record.UpdateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRulesRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.core.MessageConfigRuleService;
import top.easyblog.core.MessageTemplateService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-05-13 13:21
 */
@Slf4j
@Service
public class AdminMessagePushRecordService {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private BusinessMessageRecordService messageRecordService;

    @Autowired
    private MessageConfigRuleService messageConfigRuleService;

    /**
     * 创建推送消息
     *
     * @param request
     */
    public void create(CreateBusinessMessageRecordRequest request) {
        MessageTemplateBean messageTemplateBean = messageTemplateService.details(QueryMessageTemplateRequest.builder()
                .templateCode(request.getTemplateCode()).build());
        if (Objects.isNull(messageTemplateBean)) {
            log.info("Invalid template code:{}", request.getTemplateCode());
            throw new BusinessException(EasyResultCode.MESSAGE_TEMPLATE_NOT_FOUND);
        }

        if (!Objects.equals(MessageTemplateStatus.RELEASE.getCode(), messageTemplateBean.getStatus()) ||
                Objects.equals(Boolean.TRUE, messageTemplateBean.getDeleted())) {
                    log.info("Invalid template status:{}", request.getTemplateCode());
            throw new BusinessException(EasyResultCode.MESSAGE_TEMPLATE_STATUS_ILLEGAL);
        }

        MessageConfigRuleBean configRuleBean = messageConfigRuleService.details(QueryMessageConfigRuleRequest.builder()
                .businessModule(request.getBusinessModule()).businessEvent(request.getBusinessEvent()).build());
        if (Objects.isNull(configRuleBean)) {
            log.info("Invalid template code:{}", request.getTemplateCode());
            throw new BusinessException(EasyResultCode.MESSAGE_CONFIG_RULE_NOT_FOUND);
        }
        messageRecordService.createMessageRecord(request);
    }

    /**
     * 更新推送消息
     *
     * @param id
     * @param request
     */
    public void update(Long id, UpdateBusinessMessageRecordRequest request) {
        messageRecordService.updateMessageRecord(id, request);
    }

    /**
     * 查询推送消息详情
     *
     * @param request
     * @return
     */
    public BusinessMessageRecordBean details(QueryBusinessMessageRecordRequest request) {
        BusinessMessageRecordBean messageRecordBean = messageRecordService.details(request);
        return Optional.ofNullable(messageRecordBean).map(record -> {
            MessageConfigRuleBean messageConfigRuleBean = messageConfigRuleService.details(QueryMessageConfigRuleRequest
                    .builder().businessEvent(record.getBusinessEvent()).businessModule(record.getBusinessModule())
                    .build());
            messageRecordBean.setChannel(
                    Optional.ofNullable(messageConfigRuleBean).map(MessageConfigRuleBean::getChannel).orElse(null));
            return record;
        }).orElse(null);
    }

    /**
     * 查询推送消息列表
     *
     * @param request
     * @return
     */
    public PageResponse<BusinessMessageRecordBean> list(QueryBusinessMessageRecordsRequest request) {
        PageResponse<BusinessMessageRecordBean> response = messageRecordService.list(request);
        List<BusinessMessageRecordBean> businessMessageRecordBeans = response.getData();
        if (CollectionUtils.isEmpty(businessMessageRecordBeans)) {
            return response;
        }

        List<String> businessEvents = businessMessageRecordBeans.stream().filter(Objects::nonNull)
                .map(BusinessMessageRecordBean::getBusinessEvent).collect(Collectors.toList());
        List<String> businessEModules = businessMessageRecordBeans.stream().filter(Objects::nonNull)
                .map(BusinessMessageRecordBean::getBusinessModule).collect(Collectors.toList());
        PageResponse<MessageConfigRuleBean> configRuleBeanPageResponse = messageConfigRuleService
                .list(QueryMessageConfigRulesRequest.builder()
                        .businessEvents(businessEvents).businessModules(businessEModules).build());
        List<MessageConfigRuleBean> messageConfigRuleBeans = configRuleBeanPageResponse.getData();
        Map<String, MessageConfigRuleBean> configRuleBeanMap = messageConfigRuleBeans.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        config -> String.format("%s-%s", config.getBusinessModule(), config.getBusinessEvent()),
                        Function.identity(), (e1, e2) -> e1));

        List<BusinessMessageRecordBean> messageRecordBeans = businessMessageRecordBeans.stream().peek(record -> {
            MessageConfigRuleBean messageConfigRuleBean = configRuleBeanMap
                    .get(String.format("%s-%s", record.getBusinessModule(),
                            record.getBusinessEvent()));
            record.setChannel(
                    Optional.ofNullable(messageConfigRuleBean).map(MessageConfigRuleBean::getChannel).orElse(null));
        }).collect(Collectors.toList());
        response.setData(messageRecordBeans);

        return response;
    }
}
