package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.core.MessageTemplateService;
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
    @GetMapping("/template")
    public void  createMessageTemplate(@RequestBody @Validated CreateMessageTemplateRequest request){
        messageTemplateService.createMessageTemplate(request);
    }

}
