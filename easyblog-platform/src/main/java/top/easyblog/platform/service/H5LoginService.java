package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.service.ILoginService;

/**
 * @author: frank.huang
 * @date: 2023-08-06 16:04
 */
@Service
public class H5LoginService {

    @Autowired
    private ILoginService loginService;

    public void sendCaptchaCode(String captchaCodeType,Integer identifierType, String identifier) {
        loginService.sendCaptchaCode(captchaCodeType,identifierType, identifier);
    }

    public Object login(LoginRequest request) {
        return loginService.login(request);
    }

    public Boolean logout(LogoutRequest request) {
        return loginService.logout(request);
    }

    public Object register(RegisterUserRequest request) {
        return loginService.register(request);
    }
}
