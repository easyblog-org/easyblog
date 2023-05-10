package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.bean.TemplateValueConfigBean;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.MessageTemplateService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-02-11 14:51
 */
@RequestMapping("/v1/in")
@RestController
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @ResponseWrapper
    @PostMapping("/template")
    public void createMessageTemplate(@RequestBody @Validated CreateMessageTemplateRequest request) {
        messageTemplateService.createMessageTemplate(request);
    }

    @ResponseWrapper
    @PutMapping("/template/{code}")
    public void updateMessageTemplate(@PathVariable("code") String code, @RequestBody UpdateMessageTemplateRequest request) {
        messageTemplateService.updateMessageTemplate(code, request);
    }


    @ResponseWrapper
    @GetMapping("/template")
    public MessageTemplateBean details(@RequestParamAlias QueryMessageTemplateRequest request) {
        return messageTemplateService.details(request);
    }

    @ResponseWrapper
    @GetMapping("/templates")
    public PageResponse<MessageTemplateBean> list(@RequestParamAlias QueryMessageTemplatesRequest request) {
        return messageTemplateService.list(request);
    }

}
