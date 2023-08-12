package top.easyblog.web.api;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.request.login.UserLoginRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.platform.service.UserLoginService;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * 用户登录注册认证控制器
 *
 * @author frank.huang
 * @date 2022/01/29 15:44
 */
@RestController
@RequestMapping("/h5/v1/user")
public class UserLoginController {

    @Autowired
    private UserLoginService loginService;

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
    public Object login(@RequestBody @Valid UserLoginRequest request) {
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


    @ResponseWrapper
    @GetMapping("/logged/{token}")
    public Object logged(@PathVariable("token") String token){
        return loginService.logged(token);
    }
}
