package top.easyblog.service.strategy;


import top.easyblog.common.bean.AuthorizationBean;
import top.easyblog.common.request.login.AuthCallbackRequest;
import top.easyblog.common.request.login.OauthRequest;

/**
 * 三方登录策略
 *
 * @author: frank.huang
 * @date: 2022-02-27 18:32
 */
public interface IOauthStrategy {
    /**
     * 获取策略类型
     * @return
     */
    Integer getIdentifierType();

    /**
     * 第三方登录获取 AccessToken
     *
     * @param request
     * @return
     */
    AuthorizationBean authorize(OauthRequest request);

    /**
     * 第三方授权回调
     *
     * @param callback
     * @return
     */
    AuthorizationBean callback(AuthCallbackRequest callback);
}
