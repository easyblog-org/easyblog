package top.easyblog.core.strategy.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.request.phoneauth.CreatePhoneAuthRequest;
import top.easyblog.common.request.phoneauth.QueryPhoneAuthRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.PhoneAuthService;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.dao.auto.model.PhoneAuth;
import top.easyblog.support.util.RegexUtils;

import java.util.Objects;

/**
 * 手机密码登录
 *
 * @author: frank.huang
 * @date: 2022-02-19 17:46
 */
@Component
public class PhoneLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private PhoneAuthService phoneAuthService;


    @Override
    public Integer getIdentifierType() {
        return IdentifierType.PHONE.getSubCode();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        PhoneAuth phoneAuth = checkAndGetPhoneInfo(request);
        request.setIdentifier(String.valueOf(phoneAuth.getId()));
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = processLogin(UserDetailsBean.builder()
                .currAccount(accountBean).build(), request);
        userDetailsBean.setCurrAccount(accountBean);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }

    public PhoneAuth checkAndGetPhoneInfo(LoginRequest request) {
        // 国家区号-手机号
        String[] phoneIdentifier = request.getIdentifier().split("-");
        PhoneAuth phoneAuth = phoneAuthService.queryPhoneAuthDetails(QueryPhoneAuthRequest.builder()
                .phoneAreaCode(phoneIdentifier[0])
                .phone(phoneIdentifier[1]).build());
        if (Objects.isNull(phoneAuth)) {
            throw new BusinessException(EasyResultCode.IDENTIFIER_NOT_PHONE);
        }
        return phoneAuth;
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        String[] phoneIdentifier = request.getIdentifier().split("-");
        if (phoneIdentifier.length != 2 || Boolean.FALSE.equals(RegexUtils.isPhone(phoneIdentifier[1]))) {
            throw new BusinessException(EasyResultCode.IDENTIFIER_NOT_PHONE);
        }
        Long phoneAuthId = phoneAuthService.createPhoneAuth(CreatePhoneAuthRequest.builder()
                .phoneAreaCode(phoneIdentifier[0])
                .phone(phoneIdentifier[1])
                .build());
        request.setIdentifier(String.valueOf(phoneAuthId));
        //创建 User & Account
        UserDetailsBean userDetailsBean = processRegister(request);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }
}
