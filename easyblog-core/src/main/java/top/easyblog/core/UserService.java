package top.easyblog.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.*;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.Status;
import top.easyblog.common.enums.UserQuerySection;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.header.QueryUserHeadersRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.role.QueryRolesListRequest;
import top.easyblog.common.request.role.QueryUserRolesListRequest;
import top.easyblog.common.request.user.CreateUserRequest;
import top.easyblog.common.request.user.QueryUserListRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.request.user.UpdateUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicUserRolesService;
import top.easyblog.dao.atomic.AtomicUserService;
import top.easyblog.dao.auto.model.User;
import top.easyblog.dao.auto.model.UserRoleRelationship;
import top.easyblog.support.context.CreateOrRefreshUserRoleContext;
import top.easyblog.support.context.UserSectionContext;
import top.easyblog.support.event.UserCreateOrUpdateEvent;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author frank.huang
 * @date 2022/01/30 10:43
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private AtomicUserService atomicUserService;

    @Autowired
    private AtomicUserRolesService atomicUserRolesService;

    @Autowired
    private UserHeaderService userHeaderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 查询用户详情
     *
     * @param request
     * @return
     */
    @Transaction
    public UserDetailsBean queryUserDetails(QueryUserRequest request) {
        //1.根据request查询user基本信息
        return Optional.ofNullable(atomicUserService.queryByRequest(request)).map(user -> {
            UserDetailsBean userDetailsBean = buildUserDetailsBean(user);
            //查询其他选项参数
            fillSection(request.getSections(), Collections.singletonList(userDetailsBean));
            return userDetailsBean;
        }).orElse(null);
    }


    private UserDetailsBean buildUserDetailsBean(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserDetailsBean userDetailsBean = beanMapper.convertUser2UserBean(user);
        userDetailsBean.setIsNewUser(Boolean.FALSE);
        return userDetailsBean;
    }


    /**
     * 查看可选值
     *
     * @param section
     * @return
     */
    private UserSectionContext queryUserSectionSections(String section, Map<Long,String> userIdCodesMap) {
        UserSectionContext context = UserSectionContext.builder().build();
        if (MapUtils.isEmpty(userIdCodesMap)) {
            return context;
        }

        List<String> userCodes = new ArrayList<>(userIdCodesMap.values());
        List<Long> userIds = new ArrayList<>(userIdCodesMap.keySet());
        if (section.contains(UserQuerySection.QUERY_HEADER_IMG.getName())) {
            QueryUserHeadersRequest queryUserHeadersRequest = QueryUserHeadersRequest.builder()
                    .userCodes(userCodes).status(Status.ENABLE.getCode()).build();
            List<UserHeaderBean> userHeaderBeans = userHeaderService.queryUserHeaderBeans(queryUserHeadersRequest);
            Map<Long, List<UserHeaderBean>> userHeaderBeanMap = userHeaderBeans.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(UserHeaderBean::getUserId));
            context.setUserHistoryImagesMap(userHeaderBeanMap);
        }
        if (section.contains(UserQuerySection.QUERY_CURRENT_HEADER_IMG.getName())) {
            QueryUserHeadersRequest queryUserHeadersRequest = QueryUserHeadersRequest.builder()
                    .userCodes(userCodes).status(Status.ENABLE.getCode()).build();
            List<UserHeaderBean> userHeaderBeans = userHeaderService.queryUserHeaderBeans(queryUserHeadersRequest);
            Map<Long, UserHeaderBean> userHeaderBeanMap = userHeaderBeans.stream().
                    filter(item -> Boolean.TRUE.equals(item.getIsCurrentHeader()))
                    .collect(Collectors.toMap(UserHeaderBean::getUserId, Function.identity(), (x, y) -> x));
            context.setUserCurrentImagesMap(userHeaderBeanMap);
        }
        if (section.contains(UserQuerySection.QUERY_ACCOUNTS.getName())) {
            QueryAccountListRequest queryAccountListRequest = QueryAccountListRequest.builder()
                    .userCodes(userCodes).build();
            List<AccountBean> accounts = accountService.queryListUnlimited(queryAccountListRequest);
            Map<Long, List<AccountBean>> accountMap = accounts.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(AccountBean::getUserId));
            context.setAccountsMap(accountMap);
        }
        if (section.contains(UserQuerySection.QUERY_SIGN_LOG.getName())) {
            QueryLoginLogListRequest request = QueryLoginLogListRequest.builder()
                    .userCodes(userCodes).status(Status.ENABLE.getCode().intValue()).offset(NumberUtils.INTEGER_ZERO)
                    .limit(Constants.QUERY_LIMIT_ONE_THOUSAND).build();
            PageResponse<LoginLogBean> loginLogBeanPageResponse = loginLogService.queryLoginLogList(request);
            List<LoginLogBean> loginLogBeans = loginLogBeanPageResponse.getData();
            Map<Long, List<LoginLogBean>> loginLogBeanMap = loginLogBeans.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(LoginLogBean::getUserCode));
            context.setSignInLogsMap(loginLogBeanMap);
        }
        if (section.contains(UserQuerySection.QUERY_ROLE.getName())) {
            List<UserRoleRelationship> userRoleRelationships = atomicUserRolesService.queryList(QueryUserRolesListRequest.builder()
                    .userIds(userIds).enabled(Boolean.TRUE).build());

            if (CollectionUtils.isNotEmpty(userRoleRelationships)) {
                Map<String, UserRoleRelationship> userRoleIdMap = userRoleRelationships.stream().filter(Objects::nonNull)
                        .collect(Collectors.toMap(item -> String.format("%s-%s", item.getRoleId(), item.getUserId()), Function.identity(), (x, y) -> x));
                List<Long> roleIds = userRoleRelationships.stream().map(UserRoleRelationship::getRoleId).collect(Collectors.toList());
                PageResponse<RolesBean> rolesBeanPageResponse = rolesService.queryRolesList(QueryRolesListRequest.builder()
                        .ids(roleIds).build());
                List<RolesBean> rolesBeans = rolesBeanPageResponse.getData();
                Map<Long, List<RolesBean>> rolesIdMap = rolesBeans.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(RolesBean::getId));
                Map<Long, List<RolesBean>> userIdRoleMap = Maps.newHashMap();
                userRoleIdMap.forEach((roleUserId, userRole) -> {
                    userIdRoleMap.compute(userRole.getUserId(), (k, v) -> {
                        if (v == null) {
                            v = Lists.newArrayList();
                        }
                        List<RolesBean> rolesBeanList = rolesIdMap.get(userRole.getRoleId());
                        if (CollectionUtils.isNotEmpty(rolesBeanList)) {
                            v.addAll(rolesBeanList);
                        }
                        return v;
                    });
                });
                context.setRolesMap(userIdRoleMap);
            }
        }

        return context;
    }

    /**
     * 设置选项
     *
     * @param section
     * @param userDetailsBeans
     */
    private void fillSection(String section, List<UserDetailsBean> userDetailsBeans) {
        if (StringUtils.isBlank(section) || CollectionUtils.isEmpty(userDetailsBeans)) {
            log.info("Not found any section param or user list is empty,will not fill section");
            return;
        }

        Map<Long,String> userIdCodesMap = userDetailsBeans.stream().filter(Objects::nonNull)
               .collect(Collectors.toMap(UserDetailsBean::getId, UserDetailsBean::getCode,(x,y)->x));
        UserSectionContext context = queryUserSectionSections(section, userIdCodesMap);
        Optional.ofNullable(context).ifPresent(ctx -> {
            userDetailsBeans.stream().filter(Objects::nonNull).forEach(userDetailsBean -> {
                userDetailsBean.setUserCurrentImages(ctx.getSectionOptional(ctx.getUserCurrentImagesMap(), userDetailsBean.getId()));
                userDetailsBean.setUserHistoryImages(ctx.getSectionOptional(ctx.getUserHistoryImagesMap(), userDetailsBean.getId()));
                userDetailsBean.setAccounts(ctx.getSectionOptional(ctx.getAccountsMap(), userDetailsBean.getId()));
                userDetailsBean.setRoles(ctx.getSectionOptional(ctx.getRolesMap(), userDetailsBean.getId()));
                userDetailsBean.setSignInLogs(ctx.getSectionOptional(ctx.getSignInLogsMap(), userDetailsBean.getId()));
            });
        });
    }


    /**
     * 更新用户信息
     *
     * @param request
     */
    @Transaction
    public Long updateUser(String code, UpdateUserRequest request) {
        User user = atomicUserService.queryByRequest(QueryUserRequest.builder()
                .code(code).build());
        if (Objects.isNull(user)) {
            throw new BusinessException(EasyResultCode.USER_NOT_FOUND);
        }
        User newUser = new User();
        newUser.setId(user.getId());
        BeanUtils.copyProperties(request, newUser);
        atomicUserService.updateUserByPrimaryKey(newUser);

        //异步更新User和Role的关系
        CreateOrRefreshUserRoleContext context = CreateOrRefreshUserRoleContext.builder()
                .userId(newUser.getId()).roles(request.getRoles()).build();
        applicationEventPublisher.publishEvent(new UserCreateOrUpdateEvent(context));
        return user.getId();
    }

    /**
     * 查询列表，支持分页
     *
     * @param request
     * @return
     */
    @Transaction
    public PageResponse<UserDetailsBean> queryUserListPage(QueryUserListRequest request) {
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //没有分页参数，默认查询1000条数据
            request.setOffset(NumberUtils.INTEGER_ZERO);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.QUERY_LIMIT_ONE_THOUSAND : request.getLimit());
            List<UserDetailsBean> userDetailsBeans = buildUserDetailsBeanList(request);
            return PageResponse.<UserDetailsBean>builder().limit(request.getLimit())
                    .offset(request.getOffset()).total((long) userDetailsBeans.size()).data(userDetailsBeans).build();
        }

        long count = atomicUserService.countByRequest(request);
        if (count == 0) {
            return PageResponse.<UserDetailsBean>builder().limit(request.getLimit())
                    .offset(request.getOffset()).total(count).data(Collections.emptyList()).build();
        }

        List<UserDetailsBean> userDetailsBeans = buildUserDetailsBeanList(request);
        return PageResponse.<UserDetailsBean>builder().limit(request.getLimit())
                .offset(request.getOffset()).data(userDetailsBeans).build();
    }

    private List<UserDetailsBean> buildUserDetailsBeanList(QueryUserListRequest request) {
        List<UserDetailsBean> userDetailsBeans = atomicUserService.queryUserListByRequest(request).stream().map(user -> {
            UserDetailsBean userDetailsBean = new UserDetailsBean();
            BeanUtils.copyProperties(user, userDetailsBean);
            return userDetailsBean;
        }).collect(Collectors.toList());

        fillSection(request.getSections(), userDetailsBeans);
        return userDetailsBeans;
    }


    /**
     * 创建用户
     *
     * @param request
     */
    @Transaction
    public UserDetailsBean createUser(CreateUserRequest request) {
        User user = atomicUserService.queryByRequest(QueryUserRequest.builder()
                .nickName(request.getNickName()).build());
        if (Objects.nonNull(user)) {
            // nickname 不能重复，如果重复返回已经存在的用户
            return buildUserDetailsBean(user);
        }

        User newUser = beanMapper.convertUserCreateReq2User(request);
        newUser = atomicUserService.insertSelective(newUser);
        UserDetailsBean userDetailsBean = buildUserDetailsBean(newUser);
        Objects.requireNonNull(userDetailsBean).setIsNewUser(Boolean.TRUE);

        // 创建 or 更新用户角色
        CreateOrRefreshUserRoleContext context = CreateOrRefreshUserRoleContext.builder()
                .userId(newUser.getId()).roles(request.getRoles()).build();
        applicationEventPublisher.publishEvent(new UserCreateOrUpdateEvent(context));
        return userDetailsBean;
    }


   
}
