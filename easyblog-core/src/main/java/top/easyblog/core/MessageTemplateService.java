package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicMessageTemplateService;
import top.easyblog.dao.auto.model.MessageTemplate;

/**
 * @author: frank.huang
 * @date: 2023-02-11 14:50
 */
@Slf4j
@Service
public class MessageTemplateService {

    @Autowired
    private AtomicMessageTemplateService atomicMessageTemplateService;

    @Autowired
    private BeanMapper beanMapper;

    public void createMessageTemplate(CreateMessageTemplateRequest request) {
        MessageTemplate messageTemplate = atomicMessageTemplateService.queryByRequest(QueryMessageTemplateRequest.builder()
                .name(request.getName()).build());
        if (Objects.nonNull(messageTemplate) && Objects.equals(Boolean.FALSE, messageTemplate.getDeleted())) {
            throw new BusinessException(EasyResultCode.TEMPLATE_ALREADY_EXISTS, "The template with name " + request.getName() + " already exists.");
        }
        MessageTemplate template = beanMapper.convertBusinessMessageRecordCreateReqBusinessMessageRecord(request);
        atomicMessageTemplateService.insertOne(template);
    }

    public MessageTemplateBean details(QueryMessageTemplateRequest request) {
        MessageTemplate messageTemplate = atomicMessageTemplateService.queryByRequest(request);
        return Optional.ofNullable(messageTemplate)
                .map(template -> beanMapper.convertMessageTemplate2MessageTemplateBean(template))
                .orElse(null);
    }


    public PageResponse<MessageTemplateBean> list(QueryMessageTemplatesRequest request) {
        long count = atomicMessageTemplateService.countByRequest(request);
        if (Objects.equals(NumberUtils.LONG_ZERO, count)) {
            return PageResponse.<MessageTemplateBean>builder().total(count).offset(request.getOffset())
                    .limit(request.getLimit()).data(Collections.emptyList()).build();
        }
        List<MessageTemplate> messageTemplates = atomicMessageTemplateService.queryListByRequest(request);
        List<MessageTemplateBean> templateBeans = messageTemplates.stream().filter(Objects::nonNull)
                .map(messageTemplate -> beanMapper.convertMessageTemplate2MessageTemplateBean(messageTemplate))
                .collect(Collectors.toList());
        return PageResponse.<MessageTemplateBean>builder().total(count).offset(request.getOffset())
                .limit(request.getLimit()).data(templateBeans).build();
    }


    public void updateMessageTemplate(String code, UpdateMessageTemplateRequest request) {
        MessageTemplate template = atomicMessageTemplateService.queryByRequest(QueryMessageTemplateRequest.builder()
                .templateCode(code).build());
        if (Objects.isNull(template)) {
            throw new BusinessException(EasyResultCode.TEMPLATE_NOT_FOUND);
        }

        MessageTemplate updateTemplate = beanMapper.convertBusinessMessageRecordUpdateReqBusinessMessageRecord(template.getId(), request);
        atomicMessageTemplateService.updateByPrimaryKeySelective(updateTemplate);
    }

}
