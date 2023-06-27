package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.request.message.rule.CreateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRulesRequest;
import top.easyblog.common.request.message.rule.UpdateMessageConfigRuleRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.AdminMessagePushRuleService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-05-04 20:27
 */
@RequestMapping("/v1/message-rule-config")
@RestController
public class AdminMessageRuleConfigController {

    @Autowired
    private AdminMessagePushRuleService messagePushRuleService;

    @ResponseWrapper
    @PostMapping("")
    public void create(@RequestBody @Valid CreateMessageConfigRuleRequest request) {
        messagePushRuleService.createMessagePushRule(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void update(@PathVariable("code") String code, UpdateMessageConfigRuleRequest request) {
        messagePushRuleService.update(code, request);
    }

    @ResponseWrapper
    @GetMapping("")
    public MessageConfigRuleBean details(@RequestParamAlias QueryMessageConfigRuleRequest request) {
        return messagePushRuleService.details(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<MessageConfigRuleBean> list(@RequestParamAlias QueryMessageConfigRulesRequest request) {
        return messagePushRuleService.list(request);
    }

}
