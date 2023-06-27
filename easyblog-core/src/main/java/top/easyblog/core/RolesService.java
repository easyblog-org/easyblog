package top.easyblog.core;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.QuerySection;
import top.easyblog.common.enums.Status;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.role.CreateRolesRequest;
import top.easyblog.common.request.role.QueryRolesDetailsRequest;
import top.easyblog.common.request.role.QueryRolesListRequest;
import top.easyblog.common.request.role.QueryUserRolesListRequest;
import top.easyblog.common.request.role.UpdateRolesRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.dao.atomic.AtomicRolesService;
import top.easyblog.dao.atomic.AtomicUserRolesService;
import top.easyblog.dao.auto.model.Roles;
import top.easyblog.dao.auto.model.UserRoleRelationship;
import top.easyblog.service.section.IUserSectionInquireService;
import top.easyblog.support.context.UserSectionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:57
 */
@Service
public class RolesService implements IUserSectionInquireService {

    @Autowired
    private AtomicRolesService atomicRolesService;

    @Autowired
    private AtomicUserRolesService atomicUserRolesService;

    public Roles create(CreateRolesRequest request) {
        checkRoleNameExists(request.getName());
        return atomicRolesService.insertOne(buildCreateRoles(request));
    }

    private void checkRoleNameExists(String roleName) {
        List<Roles> rolesList = atomicRolesService.queryList(QueryRolesListRequest.builder()
                .names(Collections.singletonList(roleName)).build());
        if (CollectionUtils.isNotEmpty(rolesList)) {
            Roles role = rolesList.stream().filter(item -> {
                return Objects.nonNull(item) && StringUtils.equalsIgnoreCase(item.getName(), roleName);
            }).findAny().orElse(null);
            if (Objects.nonNull(role)) {
                throw new BusinessException(EasyResultCode.ROLE_EXISTS);
            }
        }
    }

    private Roles buildCreateRoles(CreateRolesRequest request) {
        Roles roles = new Roles();
        roles.setName(request.getName());
        roles.setDescription(request.getDescription());
        roles.setEnabled(request.getEnabled());
        return roles;
    }

    public List<RolesBean> queryAllRolesList() {
        List<Roles> roles = atomicRolesService.queryList(QueryRolesListRequest.builder()
                .enabled(Boolean.TRUE).build());
        return roles.stream().map(this::buildRolesBean).collect(Collectors.toList());
    }

    public PageResponse<RolesBean> queryRolesList(QueryRolesListRequest request) {
        long count = atomicRolesService.countByRequest(request);
        if (Objects.equals(count, NumberUtils.LONG_ZERO)) {
            return PageResponse.<RolesBean>builder().data(Collections.emptyList())
                    .limit(request.getLimit()).offset(request.getOffset()).total(count).build();
        }
        List<Roles> roles = atomicRolesService.queryList(request);
        List<RolesBean> rolesBeanList = roles.stream().map(this::buildRolesBean).collect(Collectors.toList());
        return PageResponse.<RolesBean>builder().data(rolesBeanList)
                .limit(request.getLimit()).offset(request.getOffset()).total(count).build();
    }

    public void updateRoles(String code, UpdateRolesRequest request) {
        Roles roles = atomicRolesService.queryDetails(QueryRolesDetailsRequest.builder()
                .code(code).build());
        if (Objects.isNull(roles)) {
            throw new BusinessException(EasyResultCode.ROLE_NOT_FOUND);
        }
        checkRoleNameExists(request.getName());
        atomicRolesService.updateByPrimaryKey(buildUpdateRoles(roles, request));
    }

    private Roles buildUpdateRoles(Roles oldRoles, UpdateRolesRequest request) {
        Roles roles = new Roles();
        roles.setId(oldRoles.getId());
        roles.setName(request.getName());
        roles.setDescription(request.getDescription());
        roles.setEnabled(request.getEnabled());
        return roles;
    }

    public RolesBean details(QueryRolesDetailsRequest request) {
        Roles roles = atomicRolesService.queryDetails(request);
        return buildRolesBean(roles);
    }

    private RolesBean buildRolesBean(Roles roles) {
        if (Objects.isNull(roles)) {
            return null;
        }
        RolesBean rolesBean = new RolesBean();
        rolesBean.setId(roles.getId());
        rolesBean.setCode(roles.getCode());
        rolesBean.setName(roles.getName());
        rolesBean.setEnabled(roles.getEnabled());
        rolesBean.setDescription(roles.getDescription());
        rolesBean.setCreateTime(roles.getCreateTime().getTime());
        rolesBean.setUpdateTime(roles.getUpdateTime().getTime());
        return rolesBean;
    }

    @Override
    public void execute(String section, UserSectionContext ctx, Map<Long, String> userIdCodesMap,
            boolean queryWhenSectionEmpty) {
        if (MapUtils.isEmpty(userIdCodesMap)) {
            return;
        }

        List<Long> userIds = new ArrayList<>(userIdCodesMap.keySet());
        if (section.contains(QuerySection.QUERY_ROLE.getName()) || queryWhenSectionEmpty) {
            List<UserRoleRelationship> userRoleRelationships = atomicUserRolesService
                    .queryList(QueryUserRolesListRequest.builder()
                            .userIds(userIds).enabled(Boolean.TRUE).build());

            if (CollectionUtils.isNotEmpty(userRoleRelationships)) {
                Map<String, UserRoleRelationship> userRoleIdMap = userRoleRelationships.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(item -> String.format("%s-%s", item.getRoleId(), item.getUserId()),
                                Function.identity(), (x, y) -> x));
                List<Long> roleIds = userRoleRelationships.stream().map(UserRoleRelationship::getRoleId)
                        .collect(Collectors.toList());
                PageResponse<RolesBean> rolesBeanPageResponse = queryRolesList(QueryRolesListRequest.builder()
                        .ids(roleIds).build());
                List<RolesBean> rolesBeans = rolesBeanPageResponse.getData();
                Map<Long, List<RolesBean>> rolesIdMap = rolesBeans.stream().filter(Objects::nonNull)
                        .collect(Collectors.groupingBy(RolesBean::getId));
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
                ctx.setRolesMap(userIdRoleMap);
            }
        }
    }
}
