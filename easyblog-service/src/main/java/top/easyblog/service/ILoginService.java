package top.easyblog.service;


import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.support.util.EncryptUtils;
import top.easyblog.support.util.IdGenerator;

/**
 * 系统登录认证
 *
 * @author frank.huang
 * @date 2022/01/29 14:04
 */
public interface ILoginService {

    /**
     * 登录
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean login(LoginRequest request);


    /**
     * 退出
     *
     * @param request
     */
    boolean logout(LogoutRequest request);

    /**
     * 注册
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean register(RegisterUserRequest request);


    /**
     * 生成登录token
     *
     * @return
     */
    default String generateLoginToken() {
        return EncryptUtils.MD5(IdGenerator.getUUID() + System.currentTimeMillis());
    }


    /**
     * 生成验证码
     *
     * @return
     */
    default void sendCaptchaCode(Integer identifierType, String identifier) {
        throw new UnsupportedOperationException();
    }
}
