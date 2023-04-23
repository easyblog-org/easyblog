package top.easyblog.core.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.*;
import top.easyblog.common.constant.LoginConstants;
import top.easyblog.common.enums.LoginStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogRequest;
import top.easyblog.common.request.loginlog.UpdateLoginLogRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.LoginLogService;
import top.easyblog.core.RedisService;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.core.strategy.LoginStrategyContext;
import top.easyblog.service.ILoginService;
import top.easyblog.service.strategy.ILoginStrategy;
import top.easyblog.support.util.JsonUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author frank.huang
 * @date 2022/01/29 14:09
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private RedisService redisService;


    @Override
    public void sendCaptchaCode(Integer identifierType, String identifier) {
        /*if (Objects.equals(identifierType, IdentifierType.PHONE.getSubCode()) ||
                Objects.equals(identifierType, IdentifierType.PHONE_CAPTCHA.getSubCode())) {
            ICaptchaSendStrategy captchaSendStrategy = CaptchaSendStrategyContext.getCaptchaSendStrategy(CaptchaSendChannel.PHONE_SMS.getCode());
            captchaSendStrategy.sendCaptchaCode(identifier);
        } else if (Objects.equals(identifierType, IdentifierType.E_MAIL.getSubCode())) {
            ICaptchaSendStrategy captchaSendStrategy = CaptchaSendStrategyContext.getCaptchaSendStrategy(CaptchaSendChannel.EMAIL.getCode());
            captchaSendStrategy.sendCaptchaCode(identifier);
        } else {
            throw new UnsupportedOperationException();
        }*/
    }

    @Override
    @Transaction
    public LoginDetailsBean login(LoginRequest request) {
        ILoginStrategy loginPolicy = LoginStrategyContext.getIdentifyStrategy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR);
        }
        AuthenticationDetailsBean userDetailsBean = loginPolicy.doLogin(request);
        if (Objects.isNull(userDetailsBean.getUser())) {
            throw new BusinessException(EasyResultCode.LOGIN_FAILED);
        }
        LoginDetailsBean loginDetailsBean = new LoginDetailsBean();
        loginDetailsBean.setUser(userDetailsBean.getUser());

        //通过登录日志判断用户是否已经登录过
        AccountBean currAccount = userDetailsBean.getUser().getCurrAccount();
        LoginLogBean loginLogBean = loginLogService.queryLoginLogDetails(QueryLoginLogRequest.builder()
                .userCode(currAccount.getUserCode())
                .accountCode(currAccount.getCode())
                .status(LoginStatus.ONLINE.getCode())
                .build());

        if (Objects.nonNull(loginLogBean)) {
            // 重置token过期时间
            String key = loginLogBean.getToken();
            String userInfo = redisService.get(key);
            loginDetailsBean.setToken(key);
            if (StringUtils.isBlank(userInfo)) {
                storageToken(request, loginDetailsBean);
            } else {
                redisService.expire(loginLogBean.getToken(), LoginConstants.LOGIN_TOKEN_MAX_EXPIRE, TimeUnit.DAYS);
            }
            return loginDetailsBean;
        }

        //如果用户已经登录直接返回，否则生成新的token
        loginDetailsBean.setToken(String.format("auth:token:%s", generateLoginToken()));
        storageToken(request, loginDetailsBean);
        //保存用户登录日志
        CompletableFuture.runAsync(() -> saveLoginLog(request, loginDetailsBean));
        return loginDetailsBean;
    }

    /**
     * 保存登录日志
     *
     * @param request
     * @param loginDetailsBean
     */
    private void saveLoginLog(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        UserDetailsBean userDetailsBean = loginDetailsBean.getUser();
        AccountBean currAccount = userDetailsBean.getCurrAccount();
        CreateLoginLogRequest createSignInLogRequest = Optional.of(userDetailsBean).map(userBean -> {
            log.info("User login log: user_info:{},account_info:{}", JsonUtils.toJSONString(userDetailsBean), JsonUtils.toJSONString(currAccount));
            return CreateLoginLogRequest.builder()
                    .userCode(userDetailsBean.getCode())
                    .accountCode(currAccount.getCode())
                    .token(loginDetailsBean.getToken())
                    .status(LoginStatus.ONLINE.getCode())
                    .build();
        }).orElseGet(() -> CreateLoginLogRequest.builder()
                .token(loginDetailsBean.getToken())
                .status(LoginStatus.ONLINE.getCode())
                .build());

        Optional.ofNullable(request.getExtra()).ifPresent(extra -> {
            createSignInLogRequest.setDevice(extra.getDevice());
            createSignInLogRequest.setOperationSystem(extra.getOperationSystem());
            createSignInLogRequest.setIp(extra.getIp());
            createSignInLogRequest.setLocation(extra.getLocation());
        });
        LoginLogBean loginLogBean = loginLogService.createSignInLog(createSignInLogRequest);
        log.info("Create sign log:{}", JsonUtils.toJSONString(loginLogBean));
    }

    /**
     * 存储登录token 以及 登录时的用户信息
     *
     * @param request
     * @param loginDetailsBean
     */
    private void storageToken(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        String userInfo = JsonUtils.toJSONString(loginDetailsBean.getUser());
        boolean result = redisService.set(loginDetailsBean.getToken(), userInfo, LoginConstants.LOGIN_TOKEN_MAX_EXPIRE, TimeUnit.DAYS);
        if (!result) {
            log.info("Login failed: internal error,root cause: redis return value is {}", request);
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR);
        }
    }


    @Override
    public boolean logout(LogoutRequest request) {
        String token = request.getToken();
        Boolean hasKey = redisService.exists(token);
        if (Objects.isNull(hasKey) || !hasKey) {
            throw new BusinessException(EasyResultCode.AUTH_TOKEN_NOT_FOUND);
        }
        Long expire = redisService.getExpire(token);
        if (Objects.isNull(expire) || expire < 0) {
            log.info("Token {} has expire.", token);
            return true;
        }

        String userInfoJson = redisService.get(token);
        UserDetailsBean userDetailsBean = JsonUtils.parseObject(userInfoJson, UserDetailsBean.class);
        Boolean res = redisService.expire(token, 0L, TimeUnit.NANOSECONDS);
        if (Objects.isNull(res) || !res) {
            log.info("Redis delete token failed：{}", token);
            return false;
        }

        //退出成功
        CompletableFuture.runAsync(() -> {
            AccountBean currAccount = null;
            if (Objects.nonNull(userDetailsBean) && Objects.nonNull(currAccount = userDetailsBean.getCurrAccount())) {
                //更新用户账户状态为退出
                LoginLogBean signInLogBean = loginLogService.queryLoginLogDetails(QueryLoginLogRequest.builder()
                        .userCode(userDetailsBean.getCode()).accountCode(currAccount.getCode()).token(token).build());
                Optional.ofNullable(signInLogBean).ifPresent(logBean -> {
                    UpdateLoginLogRequest updateLoginLogRequest = UpdateLoginLogRequest.builder()
                            .status(LoginStatus.OFFLINE.getCode()).build();
                    loginLogService.updateSignLog(logBean.getCode(),updateLoginLogRequest);
                });
            }
        });

        return true;
    }

    @Override
    public AuthenticationDetailsBean register(RegisterUserRequest request) {
        ILoginStrategy loginPolicy = LoginStrategyContext.getIdentifyStrategy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR);
        }
        return loginPolicy.doRegister(request);
    }

}
