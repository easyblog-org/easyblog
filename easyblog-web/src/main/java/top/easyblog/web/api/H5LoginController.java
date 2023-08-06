package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.platform.service.H5LoginService;
import top.easyblog.service.ILoginService;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * 用户登录注册认证控制器
 *
 * @author frank.huang
 * @date 2022/01/29 15:44
 */
@RestController
@RequestMapping("/h5/v1/auth")
public class H5LoginController {

    @Autowired
    private H5LoginService loginService;

    /**
     * 生成6位验证码（纯数字）
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/captcha-code")
    public void sendCaptchaCode(
            @RequestParam("captcha_code_type") String captchaCodeType,
            @RequestParam("identifier_type") Integer identifierType,
            @RequestParam("identifier") String identifier) {
        loginService.sendCaptchaCode(captchaCodeType, identifierType, identifier);
    }

    @ResponseWrapper
    @PostMapping("/login")
    public Object login(@RequestBody @Valid LoginRequest request) {
        if (IdentifierType.THIRD_IDENTITY_TYPE.contains(IdentifierType.subCodeOf(request.getIdentifierType()))) {
            throw new BusinessException(EasyResultCode.INVALID_IDENTITY_TYPE);
        }
        return loginService.login(request);
    }

    @ResponseWrapper
    @PostMapping("/logout")
    public Boolean logout(@RequestBody LogoutRequest request) {
        return loginService.logout(request);
    }

    @ResponseWrapper
    @PostMapping("/register")
    public Object register(@RequestBody @Valid RegisterUserRequest request) {
        if (IdentifierType.THIRD_IDENTITY_TYPE.contains(IdentifierType.subCodeOf(request.getIdentifierType()))) {
            throw new BusinessException(EasyResultCode.INVALID_IDENTITY_TYPE);
        }
        return loginService.register(request);
    }

}
