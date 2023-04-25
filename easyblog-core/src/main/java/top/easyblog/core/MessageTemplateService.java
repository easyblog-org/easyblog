package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
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
        //TODO 创建模板
    }

    public MessageTemplateBean details(QueryMessageTemplateRequest request) {
        MessageTemplate messageTemplate = atomicMessageTemplateService.queryByRequest(request);
        return Optional.ofNullable(messageTemplate)
                .map(template -> beanMapper.convertMessageTemplate2MessageTemplateBean(template))
                .orElse(null);
    }


    public PageResponse<MessageTemplateBean> list(QueryMessageTemplatesRequest request){
        //TODO 查询模板列表
        return PageResponse.<MessageTemplateBean>builder().build();

    }


    public void updateMessageTemplate(String code,UpdateMessageTemplateRequest request) {
       //TODO 更新模板
    }

}
