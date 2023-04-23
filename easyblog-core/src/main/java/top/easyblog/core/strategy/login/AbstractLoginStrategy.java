package top.easyblog.core.strategy.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.constant.LoginConstants;
import top.easyblog.common.enums.AccountStatus;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.enums.Status;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.request.role.QueryRolesDetailsRequest;
import top.easyblog.common.request.role.QueryUserRolesDetailsRequest;
import top.easyblog.common.request.user.CreateUserRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.AccountService;
import top.easyblog.core.UserHeaderService;
import top.easyblog.core.UserService;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.dao.atomic.AtomicRolesService;
import top.easyblog.dao.atomic.AtomicUserRolesService;
import top.easyblog.dao.auto.model.Roles;
import top.easyblog.dao.auto.model.UserRoleRelationship;
import top.easyblog.service.strategy.ILoginStrategy;
import top.easyblog.support.util.RandomNicknameUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2022-02-13 21:55
 */
@Slf4j
@Component
public abstract class AbstractLoginStrategy implements ILoginStrategy {

    //最小密码复杂度
    public static final Integer MIX_PASSWORD_COMPLEXITY = 6;

    // 默认用户角色
    public static final String DEFAULT_USER_ROLE_NAME = "NORMAL";

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserHeaderService headerService;

    @Autowired
    private AtomicUserRolesService atomicUserRolesService;

    @Autowired
    private AtomicRolesService atomicRolesService;


    /**
     * 登录前置校验
     *
     * @param request
     * @return
     */
    @Transaction
    public AccountBean preLoginVerify(LoginRequest request) {
        AccountBean accountBean = accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(IdentifierType.subCodeOf(request.getIdentifierType()).getCode())
                .identifier(request.getIdentifier())
                .build());
        if (Objects.isNull(accountBean)) {
            //检查账户不存在
            throw new BusinessException(EasyResultCode.ACCOUNT_NOT_FOUND);
        }

        if (AccountStatus.PRE_ACTIVE.getCode().equals(accountBean.getStatus())) {
            //账户未激活
            throw new BusinessException(EasyResultCode.ACCOUNT_IS_PRE_ACTIVE);
        }
        if (AccountStatus.DELETE.getCode().equals(accountBean.getStatus())) {
            //用户账户已经删除
            throw new BusinessException(EasyResultCode.ACCOUNT_IS_DELETE);
        }
        if (AccountStatus.FREEZE.getCode().equals(accountBean.getStatus())) {
            //账户被封还未解封
            throw new BusinessException(EasyResultCode.ACCOUNT_IS_FREEZE);
        }
        return accountBean;
    }

    /**
     * 默认用户名密码登录
     *
     * @param userDetailsBean
     * @return
     */
    @Transaction
    public UserDetailsBean processLogin(UserDetailsBean userDetailsBean, LoginRequest request) {
        AccountBean currAccount = userDetailsBean.getCurrAccount();
        //check request password and database password
        String databasePassword = currAccount.getCredential();
        String requestPassword = request.getCredential();
        if (StringUtils.isEmpty(requestPassword) || Boolean.FALSE.equals(requestPassword.equalsIgnoreCase(databasePassword))) {
            throw new BusinessException(EasyResultCode.PASSWORD_VALID_FAILED);
        }
        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(currAccount.getUserId())
                .sections(String.format("%s,%s,%s", LoginConstants.QUERY_CURRENT_HEADER_IMG, LoginConstants.QUERY_ROLE, LoginConstants.QUERY_CURRENT_HEADER_IMG)).build());
    }

    /**
     * 注册创建User以及Account
     *
     * @param request
     * @return
     */
    @Transaction
    public UserDetailsBean processRegister(RegisterUserRequest request) {
        //3.创建User
        String nickname = StringUtils.isNotBlank(request.getNickname()) ? request.getNickname() : RandomNicknameUtils.generateNickname();
        UserDetailsBean newUser = userService.createUser(CreateUserRequest.builder()
                .nickName(nickname).active(AccountStatus.PRE_ACTIVE.getCode()).build());

        CreateUserHeaderRequest headerImg = request.getUserHeader();
        headerService.createUserHeader(CreateUserHeaderRequest.builder()
                .userCode(newUser.getCode())
                .headerImgUrl(Optional.ofNullable(headerImg).map(CreateUserHeaderRequest::getHeaderImgUrl).orElse(headerService.getDefaultUserHeaderImg()))
                .build());

        //4. 创建账户并绑定user_id
        accountService.createAccount(CreateAccountRequest.builder()
                .userCode(newUser.getCode())
                .identityType(IdentifierType.subCodeOf(request.getIdentifierType()).getCode())
                .identifier(request.getIdentifier())
                .credential(request.getCredential())
                .verified(Objects.isNull(request.getVerified()) ? Status.DISABLE.getCode() : request.getVerified())
                .status(Objects.isNull(request.getStatus()) ? AccountStatus.PRE_ACTIVE.getCode() : request.getStatus())
                .createDirect(Boolean.TRUE)
                .build());

        //TODO 创建User role
        createUserRole(newUser);
        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(newUser.getId()).sections(LoginConstants.QUERY_ACCOUNTS).build());
    }

    private void createUserRole(UserDetailsBean newUser) {
        Roles roles = atomicRolesService.queryDetails(QueryRolesDetailsRequest.builder()
                .name(DEFAULT_USER_ROLE_NAME).build());
        if (Objects.isNull(roles)) {
            throw new BusinessException(EasyResultCode.ROLE_NOT_FOUND);
        }

        UserRoleRelationship userRoles = atomicUserRolesService.queryDetails(QueryUserRolesDetailsRequest.builder()
                .roleId(roles.getId().intValue()).userId(newUser.getId().intValue()).enabled(Boolean.TRUE)
                .build());
        if (Objects.nonNull(userRoles)) {
            log.info("Already exists user-role mapping:[uid={},roleId={}]", userRoles.getUserId(), userRoles.getRoleId());
            return;
        }
        UserRoleRelationship newUserRoles = new UserRoleRelationship();
        newUserRoles.setRoleId(roles.getId());
        newUserRoles.setUserId(newUser.getId());
        newUserRoles.setEnabled(Boolean.TRUE);
        atomicUserRolesService.insertOne(newUserRoles);
    }

    /**
     * 校验密码是否合法
     * 1.位数限制：6~12位
     * 2.字符种类限制：字母和数字以及特殊字符($,%,@,^,&)混合加密，必须出现任意两个
     *
     * @param password
     * @return
     */
    public int validatePasswdComplexity(String password) {
        int count = 0;
        if (password.length() - password.replaceAll("[A-Z]", "").length() > 0) {
            count++;
        }
        if (password.length() - password.replaceAll("[a-z]", "").length() > 0) {
            count++;
        }
        if (password.length() - password.replaceAll("[0-9]", "").length() > 0) {
            count++;
        }
        if (password.replaceAll("[0-9,A-Z,a-z]", "").length() > 0) {
            count++;
        }
        return count;
    }
}
