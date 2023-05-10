package top.easyblog.service.strategy;


import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.RegisterUserRequest;

/**
 * 系统登录策略
 *
 * @author frank.huang
 * @date 2022/01/29 16:19
 */
public interface ILoginStrategy {
    /**
     * 获取登录策略类型
     *
     * @return
     */
    Integer getIdentifierType();

    /**
     * 登录策略
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean doLogin(LoginRequest request);

    /**
     * 注册策略
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean doRegister(RegisterUserRequest request);

}
