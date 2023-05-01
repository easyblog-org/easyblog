package top.easyblog.core.strategy.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.constant.LoginConstants;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.RedisService;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.dao.auto.model.PhoneAuth;

import static top.easyblog.common.constant.LoginConstants.PHONE_LOGIN_CAPTCHA_CODE;

/**
 * 手机验证码登录
 *
 * @author: frank.huang
 * @date: 2022-02-19 19:14
 */
@Component
public class PhoneCaptchaLoginStrategy extends PhoneLoginStrategy {

    @Autowired
    private RedisService redisService;
    

    @Override
    public Integer getIdentifierType() {
        return IdentifierType.PHONE_CAPTCHA.getSubCode();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        String captchaCode = redisService.get(String.format(PHONE_LOGIN_CAPTCHA_CODE, request.getIdentifier()));
        if (StringUtils.isEmpty(captchaCode) || !StringUtils.equals(captchaCode, request.getCredential())) {
            throw new BusinessException(EasyResultCode.INCORRECT_OR_EXPIRE_CAPTCHA);
        }
        PhoneAuth phoneAuth = super.checkAndGetPhoneInfo(request);
        request.setIdentifier(String.valueOf(phoneAuth.getId()));
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = userService.queryUserDetails(QueryUserRequest.builder()
                .code(accountBean.getUserCode())
                .sections(LoginConstants.QUERY_HEADER_IMG)
                .build());
        userDetailsBean.setCurrAccount(accountBean);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        return super.doRegister(request);
    }

}
