package top.easyblog.core.strategy.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.constant.LoginConstants;
import top.easyblog.common.enums.AccountStatus;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.enums.Status;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.service.ILoginService;

import java.util.Objects;

/**
 * GitHub第三方登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Slf4j
@Component
public class GitHubLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private ILoginService loginService;


    @Override
    public Integer getIdentifierType() {
        return IdentifierType.GITHUB.getSubCode();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = userService.queryUserDetails(QueryUserRequest.builder()
                .id(accountBean.getUserId()).sections(LoginConstants.QUERY_HEADER_IMG).build());
        userDetailsBean.setCurrAccount(accountBean);
        log.info("GitHub user: {} login successfully!", request.getIdentifier());
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }

    /**
     * identifierType: IdentifierType.GitHub
     * identifier：openId
     *
     * @param request
     * @return
     */
    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        log.info("GitHub user: {} start register as user!", request.getIdentifier());
        AccountBean account = accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(IdentifierType.GITHUB.getCode()).identifier(request.getIdentifier()).build());
        if (Objects.nonNull(account)) {
            log.info("GitHub user: {} already register as user,redirect to login...", request.getIdentifier());
            return redirectToLogin(request);
        }
        request.setStatus(AccountStatus.ACTIVE.getCode());
        request.setVerified(Status.ENABLE.getCode().intValue());
        processRegister(request);
        log.info("GitHub user: {} register successfully!", request.getIdentifier());
        return redirectToLogin(request);
    }


    private AuthenticationDetailsBean redirectToLogin(RegisterUserRequest request) {
        log.info("GitHub user: {} goto login...", request.getIdentifier());
        return loginService.login(LoginRequest.builder()
                .identifierType(IdentifierType.GITHUB.getSubCode())
                .identifier(request.getIdentifier())
                .build());
    }
}
