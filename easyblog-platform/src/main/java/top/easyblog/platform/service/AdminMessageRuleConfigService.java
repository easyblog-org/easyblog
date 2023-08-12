package top.easyblog.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.MessageConfigType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.config.*;
import top.easyblog.common.request.message.rule.*;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.MessageConfigRuleService;
import top.easyblog.core.MessageConfigService;
import top.easyblog.core.convert.BeanMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminMessageRuleConfigService {

    @Autowired
    private MessageConfigService messageConfigService;

    @Autowired
    private MessageConfigRuleService messageConfigRuleService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 创建消息推送规则
     *
     * @param request
     */
    public void createMessagePushRule(CreateMessagePushRuleRequest request) {
        List<CreateMessageConfigRequest> messageParameterConfigs = request.getMessageParameterConfigs();
        List<String> configIds = createMessageConfigs(messageParameterConfigs);

        CreateMessageConfigRuleRequest messageRuleConfig = request.getMessageRuleConfig();
        messageRuleConfig.setConfigIds(StringUtils.join(configIds, Constants.COMMA));
        messageConfigRuleService.createConfigRule(messageRuleConfig);
    }

    private List<String> createMessageConfigs(List<CreateMessageConfigRequest> messageParameterConfigs) {
        if (CollectionUtils.isEmpty(messageParameterConfigs)) {
            log.info("Empty message parameter config list,ignore.");
            throw new BusinessException(EasyResultCode.MESSAGE_PARAM_CONFIG_REQUIRED);
        }

        messageParameterConfigs.stream().filter(Objects::nonNull)
                .filter(config -> StringUtils.equalsIgnoreCase(MessageConfigType.RECEIVER.name(), config.getType()))
                .findAny().orElseThrow(() -> new BusinessException(EasyResultCode.MESSAGE_PARAM_CONFIG_RECEIVER_REQUIRED));

        return messageParameterConfigs.stream().filter(Objects::nonNull).map(config -> {
            MessageConfigBean messageConfigBean = messageConfigService.createConfig(config);
            return Optional.ofNullable(messageConfigBean).map(MessageConfigBean::getCode).orElse(null);
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 更新消息推送规则
     *
     * @param request
     */
    public void update(Long id, UpdateMessagePushRuleRequest request) {
        MessageConfigRuleBean messageConfigRuleBean = messageConfigRuleService.details(QueryMessageConfigRuleRequest.builder()
                .id(id).build());
        if (Objects.isNull(messageConfigRuleBean)) {
            throw new BusinessException(EasyResultCode.MESSAGE_CONFIG_RULE_NOT_FOUND);
        }

        List<String> configIds = null;
        if (CollectionUtils.isNotEmpty(request.getConfigs())) {
            //更新配置参数
            List<UpdateMessageConfigRequest> messageParameterConfigs = request.getConfigs();
            messageParameterConfigs.stream().filter(Objects::nonNull)
                    .filter(config -> StringUtils.equalsIgnoreCase(MessageConfigType.RECEIVER.name(), config.getType()))
                    .findAny().orElseThrow(() -> new BusinessException(EasyResultCode.MESSAGE_PARAM_CONFIG_RECEIVER_REQUIRED));

            configIds = messageParameterConfigs.stream().filter(Objects::nonNull).map(config -> {
                MessageConfigBean messageConfigBean = messageConfigService.createConfig(CreateMessageConfigRequest.builder()
                        .name(config.getName())
                        .type(config.getType())
                        .templateValueConfig(CreateTemplateValueConfigRequest.builder()
                                .type(Optional.ofNullable(config.getTemplateValueConfig()).map(UpdateTemplateValueConfigRequest::getType).orElse(null))
                                .expression(Optional.ofNullable(config.getTemplateValueConfig()).map(UpdateTemplateValueConfigRequest::getExpression).orElse(null))
                                .url(Optional.ofNullable(config.getTemplateValueConfig()).map(UpdateTemplateValueConfigRequest::getUrl).orElse(null))
                                .build())
                        .build());
                return Optional.ofNullable(messageConfigBean).map(MessageConfigBean::getCode).orElse(null);
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }

        if (needUpdateMessageRuleConfig(request)) {
            String configIdStr = StringUtils.join(configIds, Constants.COMMA);
            UpdateMessageConfigRuleRequest updateMessageConfigRuleRequest = beanMapper.buildMessageRuleConfigUpdateReq(request, configIdStr);
            messageConfigRuleService.updateConfigRule(messageConfigRuleBean.getId(), updateMessageConfigRuleRequest);
        }


    }

    private boolean needUpdateMessageRuleConfig(UpdateMessagePushRuleRequest request) {
        return StringUtils.isNotBlank(request.getMsgGroup()) ||
                StringUtils.isNotBlank(request.getTemplateCode()) ||
                Objects.nonNull(request.getPriority()) ||
                Objects.nonNull(request.getChannel());
    }

    /**
     * 查询消息推送规则详情
     *
     * @param request
     * @return
     */
    public MessageConfigRuleBean details(QueryMessageConfigRuleRequest request) {
        MessageConfigRuleBean messageConfigRuleBean = messageConfigRuleService.details(QueryMessageConfigRuleRequest.builder()
                .businessEvent(request.getBusinessEvent()).businessModule(request.getBusinessModule()).code(request.getCode()).build());
        return Optional.ofNullable(messageConfigRuleBean).map(configRule -> {
            List<String> configIds = Arrays.stream(StringUtils.split(configRule.getConfigIds(), Constants.COMMA)).collect(Collectors.toList());
            PageResponse<MessageConfigBean> configBeanPageResponse = messageConfigService.list(QueryMessageConfigsRequest.builder()
                    .codes(configIds).build());
            if (Objects.isNull(configBeanPageResponse) || CollectionUtils.isNotEmpty(configBeanPageResponse.getData())) {
                return configRule;
            }

            configRule.setConfigs(configBeanPageResponse.getData());
            return configRule;
        }).orElse(null);
    }

    /**
     * 查询消息推送规则列表
     *
     * @param request
     * @return
     */
    public PageResponse<MessageConfigRuleBean> list(QueryMessageConfigRulesRequest request) {
        PageResponse<MessageConfigRuleBean> configRuleBeanPageResponse = messageConfigRuleService.list(QueryMessageConfigRulesRequest.builder()
                .businessEvents(request.getBusinessEvents())
                .businessModules(request.getBusinessModules())
                .templateCode(request.getTemplateCode())
                .channel(request.getChannel())
                .deleted(request.getDeleted())
                .limit(request.getLimit())
                .offset(request.getOffset()).build());
        PageResponse<MessageConfigRuleBean> pageResponse = PageResponse.<MessageConfigRuleBean>builder()
                .offset(request.getOffset())
                .limit(request.getLimit())
                .total(NumberUtils.LONG_ZERO)
                .data(Collections.emptyList()).build();
        if (Objects.isNull(configRuleBeanPageResponse) || CollectionUtils.isEmpty(configRuleBeanPageResponse.getData())) {
            return pageResponse;
        }

        List<MessageConfigRuleBean> messageConfigRuleBeans = configRuleBeanPageResponse.getData();
        pageResponse.setData(messageConfigRuleBeans);
        List<String> configIds = messageConfigRuleBeans.stream().map(config -> StringUtils.split(config.getConfigIds(), Constants.COMMA))
                .flatMap(Arrays::stream)
                .distinct().collect(Collectors.toList());
        PageResponse<MessageConfigBean> configBeanPageResponse = messageConfigService.list(QueryMessageConfigsRequest.builder()
                .codes(configIds).build());
        if (Objects.isNull(configBeanPageResponse) || CollectionUtils.isEmpty(configBeanPageResponse.getData())) {
            return pageResponse;
        }

        List<MessageConfigBean> messageConfigBeans = configBeanPageResponse.getData();
        Map<String, MessageConfigBean> configBeanMap = messageConfigBeans.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(MessageConfigBean::getCode, Function.identity(), (x, y) -> x));

        List<MessageConfigRuleBean> messagePushRuleBeans = pageResponse.getData();
        messagePushRuleBeans = messagePushRuleBeans.stream().filter(Objects::nonNull).peek(pushRuleBean -> {
            List<String> configIdList = Arrays.stream(StringUtils.split(pushRuleBean.getConfigIds(), Constants.COMMA)).collect(Collectors.toList());
            List<MessageConfigBean> configBeanList = configIdList.stream()
                    .filter(Objects::nonNull).map(configBeanMap::get).filter(Objects::nonNull).collect(Collectors.toList());
            pushRuleBean.setConfigs(configBeanList);
        }).collect(Collectors.toList());

        pageResponse.setData(messagePushRuleBeans);
        return pageResponse;
    }

    /**
     * 查询消息推送规则（树形结构/级联结构）
     *
     * @param templateCode
     * @return
     */
    public Map<String, Map<String, List<MessageConfigRuleBean>>> queryMessagePushRuleMap(String templateCode) {
        PageResponse<MessageConfigRuleBean> ruleBeanPageResponse = list(QueryMessageConfigRulesRequest.builder().templateCode(templateCode).build());
        if (Objects.isNull(ruleBeanPageResponse) || CollectionUtils.isEmpty(ruleBeanPageResponse.getData())) {
            return Collections.emptyMap();
        }

        return ruleBeanPageResponse.getData().stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(MessageConfigRuleBean::getBusinessModule, Collectors.groupingBy(MessageConfigRuleBean::getBusinessEvent)));
    }
}
