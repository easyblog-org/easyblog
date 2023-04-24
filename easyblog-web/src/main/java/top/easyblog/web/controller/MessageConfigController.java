package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.request.message.config.CreateMessageConfigRequest;
import top.easyblog.common.request.message.config.QueryMessageConfigRequest;
import top.easyblog.common.request.message.config.QueryMessageConfigsRequest;
import top.easyblog.common.request.message.config.UpdateMessageConfigRequest;
import top.easyblog.core.MessageConfigService;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:49
 */
@RestController
@RequestMapping("/v1")
public class MessageConfigController {

    @Autowired
    private MessageConfigService messageConfigService;

    /**
     * 创建通知配置参数
     *
     * @return
     */
    @ResponseWrapper
    @PostMapping("/message-config")
    public MessageConfigBean createConfig(@RequestBody CreateMessageConfigRequest request) {
        return messageConfigService.createConfig(request);
    }

    /**
     * 创建通知配置参数
     *
     * @return
     */
    @ResponseWrapper
    @PutMapping("/message-config/{code}")
    public void updateConfig(@PathVariable("code") String code,
                             @RequestBody UpdateMessageConfigRequest request) {
        messageConfigService.updateConfig(code,request);
    }


    /**
     * 查询通知配置详情
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/message-config")
    public MessageConfigBean details(QueryMessageConfigRequest request) {
        return messageConfigService.details(request);
    }

    /**
     * 查询通知配置列表
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/message-configs")
    public Object list(QueryMessageConfigsRequest request) {
        return messageConfigService.list(request);
    }


}
