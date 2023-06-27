package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.MessageTemplateService;

/**
 * @author: frank.huang
 * @date: 2023-05-01 18:53
 */
@Service
public class AdminMessageTemplateService {

    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * 创建消息模板
     * 
     * @param request
     */
    public void create(CreateMessageTemplateRequest request) {
        messageTemplateService.createMessageTemplate(request);
    }

    /**
     * 更新消息模板
     * 
     * @param code
     * @param request
     */
    public void update(String code, UpdateMessageTemplateRequest request) {
        messageTemplateService.updateMessageTemplate(code, request);
    }

    /**
     * 查询消息模板详情
     * 
     * @param request
     * @return
     */
    public MessageTemplateBean details(QueryMessageTemplateRequest request) {
        return messageTemplateService.details(request);
    }

    /**
     * 查询消息模板列表
     * 
     * @param request
     * @return
     */
    public PageResponse<MessageTemplateBean> list(QueryMessageTemplatesRequest request) {
        return messageTemplateService.list(request);
    }

}
