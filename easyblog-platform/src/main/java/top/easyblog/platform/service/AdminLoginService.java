package top.easyblog.platform.service;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.LoginDetailsBean;
import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.bean.UserAvatarBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.common.request.login.AdminLoginRequest;
import top.easyblog.common.request.login.AdminPasswordModifyRequest;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.LogoutRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.AccountService;
import top.easyblog.core.LoginLogService;
import top.easyblog.core.LoginService;
import top.easyblog.core.UserAvatarService;
import top.easyblog.core.UserService;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.support.util.JsonUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2023-03-03 20:21
 */
@Slf4j
@Service
public class AdminLoginService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserAvatarService userAvatarServce;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 管理员登录
     * 
     * @param request
     * @return
     */
    public LoginDetailsBean login(AdminLoginRequest request) {
        // 查询登录邮箱账号是否存在：一期只支持邮箱登录，长期计划登录方式动态传递
        AccountBean accountBean = Optional.ofNullable(accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(IdentifierType.E_MAIL.getCode())
                .identifier(request.getEmail())
                .build())).orElseThrow(() -> new BusinessException(EasyResultCode.ACCOUNT_NOT_FOUND));

        UserDetailsBean userDetailsBean = userService.queryUserDetails(QueryUserRequest.builder()
                .code(accountBean.getUserCode()).sections("roles").build());
        Optional.ofNullable(userDetailsBean).map(item -> {
            List<RolesBean> roles = item.getRoles();
            if (CollectionUtils.isEmpty(roles)) {
                return null;
            }

            return roles.stream().filter(role -> {
                // 只有管理员，负责人可以登录
                return StringUtils.equalsIgnoreCase("admin", role.getName()) ||
                        StringUtils.equalsIgnoreCase("root", role.getName()) ||
                        StringUtils.equalsIgnoreCase("owner", role.getName());
            }).findFirst().orElse(null);
        }).orElseThrow(() -> {
            log.info("User not admin,can not login.Details==>{}", JsonUtils.toJSONString(request));
            return new BusinessException(EasyResultCode.NO_ACCESS_PERMISSION);
        });

        LoginRequest loginRequest = beanMapper.buildUserLoginRequest(request);
        loginRequest.setExtra(beanMapper.buildUserSignLogReqeust(request, accountBean));
        return loginService.login(loginRequest);
    }

    /**
     * 管理员退出登录
     * 
     * @param token
     * @return
     */
    public Boolean logout(String token) {
        return loginService.logout(LogoutRequest.builder().token(token).build());
    }

    /**
     * 修改密码
     * 
     * @param request
     */
    public void modifyPassword(AdminPasswordModifyRequest request) {
        AccountBean accountBean = Optional
                .ofNullable(accountService.queryAccountDetails(QueryAccountRequest.builder()
                        .identityType(IdentifierType.E_MAIL.getCode())
                        .identifier(request.getIdentify())
                        .build()))
                .orElseThrow(() -> new BusinessException(EasyResultCode.ACCOUNT_NOT_FOUND));

        if (!StringUtils.equals(accountBean.getCredential(), request.getPassword())) {
            throw new BusinessException(EasyResultCode.PASSWORD_VALID_FAILED);
        }

        if (StringUtils.equals(request.getPassword(), request.getConfigPassword())) {
            throw new BusinessException(EasyResultCode.PASSWORD_NOT_CHANGE);
        }

        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setCredential(request.getConfigPassword());
        accountService.updateAccount(accountBean.getCode(), updateAccountRequest);
    }

    /**
     * 查询登录日志
     * 
     * @param request
     * @return
     */
    public PageResponse<LoginLogBean> queryLoginLogs(QueryLoginLogListRequest request) {
        return loginLogService.queryLoginLogList(request);
    }

    /**
     * 刷新登录状态
     * 
     * @param userCode
     * @param accountCode
     * @return
     */
    public AuthenticationDetailsBean refresh(String userCode, String accountCode) {
        UserDetailsBean userDetailsBean = userService
                .queryUserDetails(QueryUserRequest.builder().code(userCode).build());
        return Optional.ofNullable(userDetailsBean).map(bean -> {
            AccountBean accountBean = accountService
                    .queryAccountDetails(QueryAccountRequest.builder().code(accountCode).build());
            bean.setCurrAccount(accountBean);

            UserAvatarBean userHeaderImgBean = userAvatarServce
                    .queryUserHeaderDetails(QueryUserHeaderImgRequest.builder()
                            .userCode(userCode).status((byte) BooleanUtils.toInteger(Boolean.TRUE)).build());
            bean.setUserCurrentImages(userHeaderImgBean);
            return AuthenticationDetailsBean.builder().user(bean).build();
        }).orElse(null);
    }
}
