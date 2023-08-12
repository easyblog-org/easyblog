package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.login.UserLoginRequest;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.AccountService;
import top.easyblog.core.RedisService;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.service.ILoginService;

import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2023-08-06 16:04
 */
@Service
public class UserLoginService {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private RedisService redisService;

    public void sendCaptchaCode(String captchaCodeType, Integer identifierType, String identifier) {
        loginService.sendCaptchaCode(captchaCodeType, identifierType, identifier);
    }

    public Object login(UserLoginRequest request) {
        IdentifierType identifierType = IdentifierType.subCodeOf(request.getIdentifierType());
        Assert.notNull(identifierType, "Illegal login entrance.....");

        AccountBean accountBean = Optional.ofNullable(accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(identifierType.getCode())
                .identifier(request.getIdentifier())
                .build())).orElseThrow(() -> new BusinessException(EasyResultCode.ACCOUNT_NOT_FOUND));


        LoginRequest loginRequest = beanMapper.buildUserLoginRequest(request);
        loginRequest.setExtra(beanMapper.buildUserSignLogReqeust(request, accountBean));
        return loginService.login(loginRequest);
    }

    public Boolean logout(LogoutRequest request) {
        return loginService.logout(request);
    }

    public Object register(RegisterUserRequest request) {
        return loginService.register(request);
    }

    public boolean logged(String token) {
        return redisService.exists(token);
    }
}
