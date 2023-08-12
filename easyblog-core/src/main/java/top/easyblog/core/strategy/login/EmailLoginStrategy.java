package top.easyblog.core.strategy.login;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.LoginDetailsBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.enums.AccountStatus;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.RedisService;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.support.util.ConcurrentUtils;
import top.easyblog.support.util.RegexUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * 邮箱账号登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Component
public class EmailLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private RedisService redisService;

    @Override
    public Integer getIdentifierType() {
        return IdentifierType.E_MAIL.getSubCode();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = UserDetailsBean.builder().currAccount(accountBean).build();
        userDetailsBean = processLogin(userDetailsBean, request);
        userDetailsBean.setCurrAccount(accountBean);
        return LoginDetailsBean.builder().user(userDetailsBean).build();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        if (Boolean.FALSE.equals(RegexUtils.isEmail(request.getIdentifier()))) {
            throw new BusinessException(EasyResultCode.IDENTIFIER_NOT_EMAIL);
        }

        IdentifierType identifierType = IdentifierType.subCodeOf(request.getIdentifierType());
        QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                .identityType(identifierType.getCode())
                .identifier(request.getIdentifier()).build();
        AccountBean account = accountService.queryAccountDetails(queryAccountRequest);
        if (Objects.nonNull(account)) {
            throw new BusinessException(EasyResultCode.EMAIL_ACCOUNT_EXISTS);
        }

        String captchaCodeKey = String.format("CAPTCHA_REGISTER:%s:%s",
                Objects.requireNonNull(identifierType, "Illegal identifier type").getCode(),
                request.getIdentifier());
        String captchaCode = redisService.get(captchaCodeKey);
        if (StringUtils.isBlank(captchaCode)) {
            throw new BusinessException(EasyResultCode.CAPTCHA_EXPIRED_OR_NOT_EXISTS);
        }

        // 使用之后立即删除验证码
        ConcurrentUtils.asyncRunSingleTask(() -> redisService.delete(captchaCodeKey));

        //检查密码是否符合
        if (Boolean.FALSE.equals(StringUtils.equals(request.getCredentialAgain(), captchaCode))) {
            throw new BusinessException(EasyResultCode.PASSWORD_NOT_EQUAL);
        }

        //创建 User & Account
        request.setVerified(BooleanUtils.toInteger(Boolean.TRUE));
        request.setStatus(AccountStatus.ACTIVE.getCode());
        UserDetailsBean userDetailsBean = processRegister(request);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }


}
