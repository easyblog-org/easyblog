package top.easyblog.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.easyblog.core.StaticService;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-02-26 21:01
 */
@RestController
@RequestMapping("/v1/static")
public class StaticController {

    @Autowired
    private StaticService staticService;


    @ResponseWrapper
    @GetMapping("/identity_types")
    public Object queryAllIdentityType() {
        return staticService.queryAllIdentityType();
    }

    @ResponseWrapper
    @GetMapping("/continents")
    public Object queryAllContinent() {
        return staticService.queryAllContinent();
    }

    @ResponseWrapper
    @GetMapping("/msg-type")
    public Object queryAllMsgType() {
        return staticService.queryAllMessageType();
    }

    @ResponseWrapper
    @GetMapping("/msg-shield-type")
    public Object queryAllMsgShieldType() {
        return staticService.queryAllMessageShieldType();
    }

    @ResponseWrapper
    @GetMapping("/msg-config-type")
    public Object queryAllMessageConfigype() {
        return staticService.queryAllMessageConfigype();
    }


    @ResponseWrapper
    @GetMapping("/msg-push-channel")
    public Object queryAllMessagePushChannel() {
        return staticService.queryAllMessagePushChannel();
    }

    @ResponseWrapper
    @GetMapping("/msg-template-value-type")
    public Object queryAllMessageTemplateValueType() {
        return staticService.queryAllMessageTemplateValueType();
    }

}
