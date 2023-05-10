package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.enums.MessageConfigType;
import top.easyblog.common.enums.TemplateValueConfigType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.config.*;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicMessageConfigService;
import top.easyblog.dao.atomic.AtomicTemplateValueConfigService;
import top.easyblog.dao.auto.model.MessageConfig;
import top.easyblog.dao.auto.model.TemplateValueConfig;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 通知消息参数配置
 *
 * @author: frank.huang
 * @date: 2023-02-04 19:47
 */
@Slf4j
@Service
public class MessageConfigService {

    @Autowired
    private AtomicMessageConfigService atomicMessageConfigService;

    @Autowired
    private AtomicTemplateValueConfigService atomicTemplateValueConfigService;

    @Autowired
    private BeanMapper beanMapper;

    @Transaction
    public MessageConfigBean createConfig(CreateMessageConfigRequest request) {
        MessageConfigType messageConfigType = MessageConfigType.of(request.getType());
        if (Objects.isNull(messageConfigType)) {
            throw new BusinessException(EasyResultCode.ILLEGAL_CONFIG_TYPE);
        }
        TemplateValueConfigType templateValueConfigType = TemplateValueConfigType.codeOf(request.getTemplateValueConfig().getType());
        if (Objects.isNull(templateValueConfigType)) {
            throw new BusinessException(EasyResultCode.ILLEGAL_TEMPLATE_VALUE_TYPE);
        }
        TemplateValueConfig templateValueConfig = beanMapper.buildTemplateConfig(request.getTemplateValueConfig());
        atomicTemplateValueConfigService.insertOne(templateValueConfig);
        MessageConfig messageConfig = beanMapper.buildMessageConfig(request, templateValueConfig.getId());
        atomicMessageConfigService.insertOne(messageConfig);
        return beanMapper.buildMessageConfigBean(messageConfig, templateValueConfig);
    }


    @Transaction
    public void updateConfig(String code, UpdateMessageConfigRequest request) {
        MessageConfig messageConfig = atomicMessageConfigService.queryByRequest(QueryMessageConfigRequest.builder()
                .code(code).build());
        if(Objects.isNull(messageConfig)){
            throw new BusinessException(EasyResultCode.MESSAGE_CONFIG_NOT_FOUND);
        }
        MessageConfig newMessageConfig = beanMapper.buildMessageConfig(request, messageConfig.getId());
        atomicMessageConfigService.updateByPKSelective(newMessageConfig);

        TemplateValueConfig templateValueConfig = beanMapper.buildTemplateConfig(request.getTemplateValueConfig(), messageConfig.getTemplateValueConfigId());
        atomicTemplateValueConfigService.updateByPKSelective(templateValueConfig);
    }

    public MessageConfigBean details(QueryMessageConfigRequest request) {
        MessageConfig messageConfig = atomicMessageConfigService.queryByRequest(request);
        if (Objects.isNull(messageConfig)) {
            return null;
        }
        TemplateValueConfig templateValueConfig = atomicTemplateValueConfigService.queryByRequest(QueryTemplateValueConfigRequest.builder()
                .id(messageConfig.getId()).build());
        return beanMapper.buildMessageConfigBean(messageConfig, templateValueConfig);
    }

    public List<MessageConfigBean> listAll(QueryMessageConfigsRequest request) {
       return atomicMessageConfigService.queryListByRequest(request);
    }


    public PageResponse<MessageConfigBean> list(QueryMessageConfigsRequest request) {
        long amount = atomicMessageConfigService.countByRequest(request);
        if (Objects.equals(NumberUtils.LONG_ZERO, amount)) {
            return PageResponse.<MessageConfigBean>builder().total(amount).offset(request.getOffset()).limit(request.getLimit()).data(Collections.emptyList()).build();
        }
        List<MessageConfigBean> messageConfigBeans = atomicMessageConfigService.queryListByRequest(request);
        return PageResponse.<MessageConfigBean>builder().total(amount).limit(request.getLimit()).offset(request.getOffset()).data(messageConfigBeans).build();
    }
}
