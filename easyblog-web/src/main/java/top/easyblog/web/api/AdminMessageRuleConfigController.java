package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.request.message.rule.*;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.AdminMessageRuleConfigService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2023-05-04 20:27
 */
@RequestMapping("/admin/v1/message-rule-config")
@RestController
public class AdminMessageRuleConfigController {

    @Autowired
    private AdminMessageRuleConfigService messagePushRuleService;

    @ResponseWrapper
    @PostMapping("")
    public void create(@RequestBody @Valid CreateMessagePushRuleRequest request) {
        messagePushRuleService.createMessagePushRule(request);
    }


    @ResponseWrapper
    @PutMapping("/{id}")
    public void update(
            @PathVariable("id") Long id,
            @RequestBody UpdateMessagePushRuleRequest request) {
        messagePushRuleService.update(id,request);
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

    @ResponseWrapper
    @GetMapping("/cascader")
    public Map<String, Map<String, List<MessageConfigRuleBean>>> queryMessagePushRuleMap(@RequestParam("template_code") String templateCode) {
        return messagePushRuleService.queryMessagePushRuleMap(templateCode);
    }

}
