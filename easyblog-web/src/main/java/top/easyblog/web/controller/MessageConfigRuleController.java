package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.request.message.rule.CreateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRulesRequest;
import top.easyblog.common.request.message.rule.UpdateMessageConfigRuleRequest;
import top.easyblog.core.MessageConfigRuleService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:06
 */
@RestController
@RequestMapping("/v1/in")
public class MessageConfigRuleController {

    @Autowired
    private MessageConfigRuleService messageConfigRuleService;


    @ResponseWrapper
    @PostMapping("/config-rule")
    public void createConfigRule(@RequestBody @Valid CreateMessageConfigRuleRequest request) {
        messageConfigRuleService.createConfigRule(request);
    }

    @ResponseWrapper
    @PostMapping("/config-rule/{id}")
    public void updateConfigRule(@PathVariable("id") Long id ,
                                 @RequestBody UpdateMessageConfigRuleRequest request) {
        messageConfigRuleService.updateConfigRule(id, request);
    }

    @ResponseWrapper
    @GetMapping("/config-rule")
    public MessageConfigRuleBean details(@RequestParamAlias QueryMessageConfigRuleRequest request){
       return messageConfigRuleService.details(request);
    }

    @ResponseWrapper
    @GetMapping("/config-rules")
    public Object list(@RequestParamAlias QueryMessageConfigRulesRequest request){
        return messageConfigRuleService.list(request);
    }
}
