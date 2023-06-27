package top.easyblog.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.AdminMessageTemplateService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-25 13:48
 */
@RequestMapping("/v1/template")
@RestController
public class MessageTemplateController {


    @Autowired
    private AdminMessageTemplateService templateService;


    @ResponseWrapper
    @PostMapping("")
    public void createTemplate(@RequestBody @Valid CreateMessageTemplateRequest request) {
        templateService.create(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void updateUserAccount(@PathVariable("code") String code, @RequestBody @Valid UpdateMessageTemplateRequest request) {
        templateService.update(code, request);
    }


    @ResponseWrapper
    @GetMapping("")
    public MessageTemplateBean details(@RequestParamAlias QueryMessageTemplateRequest request) {
        return templateService.details(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<MessageTemplateBean> queryList(@RequestParamAlias QueryMessageTemplatesRequest request) {
        return templateService.list(request);
    }


}
