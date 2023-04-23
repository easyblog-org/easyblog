package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.AuthorizationBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.AuthCallbackRequest;
import top.easyblog.common.request.login.OauthRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.strategy.Oauth2StrategyContext;
import top.easyblog.service.strategy.IOauthStrategy;


import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:31
 */
@Slf4j
@Service
public class Oauth2Service {


    public AuthorizationBean authorize(OauthRequest request) {
        IOauthStrategy oauthPolicy = Oauth2StrategyContext.getOauthStrategy(request.getIdentifierType());
        if (Objects.isNull(oauthPolicy)) {
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR);
        }
        return oauthPolicy.authorize(request);
    }

    public AuthorizationBean callback(AuthCallbackRequest authCallback) {
        IOauthStrategy oauthPolicy = Oauth2StrategyContext.getOauthStrategy(authCallback.getPlatform());
        if (Objects.isNull(oauthPolicy)) {
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR);
        }
        return oauthPolicy.callback(authCallback);
    }
}
