package top.easyblog.platform.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.request.user.CreateUserAccountRequest;
import top.easyblog.common.request.user.CreateUserRequest;
import top.easyblog.common.request.user.QueryUserListRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.request.user.UpdateUserAccountRequest;
import top.easyblog.common.request.user.UpdateUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.AccountService;
import top.easyblog.core.UserService;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.support.util.JsonUtils;

@Slf4j
@Service
public class AdminUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 查询用户详情
     * 
     * @param request
     * @return
     */
    public UserDetailsBean details(QueryUserRequest request) {
        return userService.queryUserDetails(request);
    }

    /**
     * 查询用户列表
     * 
     * @param request
     * @return
     */
    public PageResponse<UserDetailsBean> queryUserList(QueryUserListRequest request) {
        return userService.queryUserListPage(request);
    }

    /**
     * 创建用户信息
     * 
     * @param request
     * @return
     */
    public UserDetailsBean createUserAccount(CreateUserAccountRequest request) {
        CreateUserRequest createUserRequest = beanMapper.buildUserCreateRequest(request);
        UserDetailsBean userDetailsBean = userService.createUser(createUserRequest);
        if (Objects.isNull(userDetailsBean)) {
            // 创建失败
            log.info("Create admin user failed.Details==>{}", JsonUtils.toJSONString(request));
            throw new BusinessException(EasyResultCode.CREATE_USER_FAILED);
        }

        CreateAccountRequest createAccountRequest = beanMapper.buildCreateAccountRequest(request,
                userDetailsBean.getCode());
        accountService.createAccount(createAccountRequest);
        return userDetailsBean;
    }

    /**
     * 更新用户信息
     * 
     * @param userCode
     * @param request
     */
    public void updateUserAccount(String userCode, UpdateUserAccountRequest request) {
        UserDetailsBean userDetailsBean = userService
                .queryUserDetails(QueryUserRequest.builder().code(userCode).build());
        if (Objects.isNull(userDetailsBean)) {
            throw new BusinessException(EasyResultCode.USER_NOT_FOUND);
        }

        UpdateUserRequest updateUserAccountRequest = beanMapper.buildUserCreateRequest(request);
        userService.updateUser(userCode, updateUserAccountRequest);

        Optional.ofNullable(request.getIdentityType()).ifPresent(identityType -> {
            AccountBean accountBean = accountService.queryAccountDetails(QueryAccountRequest.builder()
                    .userCode(userDetailsBean.getCode()).identityType(identityType).build());
            if (Objects.isNull(accountBean)) {
                CreateAccountRequest createAccountRequest = beanMapper
                        .buildCreateAccountRequest(CreateUserAccountRequest.builder()
                                .identityType(identityType).email(request.getEmail()).password(request.getPassword())
                                .build(), userDetailsBean.getCode());
                accountService.createAccount(createAccountRequest);
                return;
            }

            UpdateAccountRequest updateAccountRequest = beanMapper.buildAccountUpdateRequest(request);
            accountService.updateByIdentityType(userDetailsBean.getCode(), identityType, updateAccountRequest);
        });
    }

}
