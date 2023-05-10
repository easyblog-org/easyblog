package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.common.bean.AuthorizationBean;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.AuthCallbackRequest;
import top.easyblog.common.request.login.OauthRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.Oauth2Service;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * 第三方登录控制器
 *
 * @author: frank.huang
 * @date: 2022-02-27 14:51
 */
@RestController
@RequestMapping("/v1/open")
public class OauthController {

    @Autowired
    private Oauth2Service oauthService;

    @ResponseWrapper
    @GetMapping(value = "/authorize")
    public AuthorizationBean authorize(@RequestParamAlias @Valid OauthRequest request) {
        return oauthService.authorize(request);
    }

    @ResponseWrapper
    @GetMapping(value = "/callback/{platform}")
    public AuthorizationBean callback(@PathVariable("platform") Integer platform, @RequestParamAlias @Valid AuthCallbackRequest callback) {
        if (IdentifierType.SYSTEM_IDENTITY_TYPE.contains(IdentifierType.codeOf(callback.getPlatform()))) {
            throw new BusinessException(EasyResultCode.INVALID_IDENTITY_TYPE);
        }
        callback.setPlatform(platform);
        return oauthService.callback(callback);
    }

}
