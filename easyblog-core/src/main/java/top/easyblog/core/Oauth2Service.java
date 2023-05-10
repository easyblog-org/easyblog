package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.AuthorizationBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.AuthCallbackRequest;
import top.easyblog.common.request.login.OauthRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.strategy.Oauth2StrategyContext;
import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:31
 */
@Slf4j
@Service
public class Oauth2Service {


    public AuthorizationBean authorize(OauthRequest request) {
        return Optional.ofNullable(Oauth2StrategyContext.getOauthStrategy(request.getIdentifierType())).map(policy->{
            log.info("Oauth 2 authorize plaform : %s",policy.getIdentifierType());
            return policy.authorize(request);
        }).orElseThrow(()->new BusinessException(EasyResultCode.INCORRECT_OAUTH2_POLICY,"Unkown oauth policy!"));
    }

    public AuthorizationBean callback(AuthCallbackRequest authCallback) {
        return Optional.ofNullable(Oauth2StrategyContext.getOauthStrategy(authCallback.getPlatform())).map(policy->{
            log.info("Oauth 2 callback plaform : %s",policy.getIdentifierType());
            return policy.callback(authCallback);
        }).orElseThrow(()->new BusinessException(EasyResultCode.INCORRECT_OAUTH2_POLICY,"Unkown oauth policy!"));
    }
}
