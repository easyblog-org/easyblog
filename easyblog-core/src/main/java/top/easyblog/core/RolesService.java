package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.role.CreateRolesRequest;
import top.easyblog.common.request.role.QueryRolesDetailsRequest;
import top.easyblog.common.request.role.QueryRolesListRequest;
import top.easyblog.common.request.role.UpdateRolesRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.dao.atomic.AtomicRolesService;
import top.easyblog.dao.auto.model.Roles;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:57
 */
@Slf4j
@Service
public class RolesService {

    @Autowired
    private AtomicRolesService atomicRolesService;


    public Roles create(CreateRolesRequest request) {
        checkRoleNameExists(request.getCode());
        return atomicRolesService.insertOne(buildCreateRoles(request));
    }


    private void checkRoleNameExists(String code) {
        List<Roles> rolesList = atomicRolesService.queryList(QueryRolesListRequest.builder()
                .codes(Collections.singletonList(code)).build());
        if (CollectionUtils.isNotEmpty(rolesList)) {
            Roles role = rolesList.stream().filter(item -> {
                //TODO name is string?
                //return Objects.nonNull(item) && StringUtils.equalsIgnoreCase(item.getName(), code);
                return true;
            }).findAny().orElse(null);
            if (Objects.nonNull(role)) {
                throw new BusinessException(EasyResultCode.ROLE_EXISTS);
            }
        }
    }

    private Roles buildCreateRoles(CreateRolesRequest request) {
        Roles roles = new Roles();
        roles.setCode(request.getCode());
        //roles.setName(request.getName());
        roles.setDesc(request.getDescription());
        roles.setEnabled(request.getEnabled());
        return roles;
    }

    public List<RolesBean> queryAllRolesList(){
        List<Roles> roles = atomicRolesService.queryList(QueryRolesListRequest.builder()
                .enabled(Boolean.TRUE).build());
        return roles.stream().map(this::buildRolesBean).collect(Collectors.toList());
    }

    public PageResponse<RolesBean> queryRolesList(QueryRolesListRequest request) {
        long count = atomicRolesService.countByRequest(request);
        if (Objects.equals(count, NumberUtils.LONG_ZERO)) {
            return PageResponse.<RolesBean>builder().data(Collections.emptyList())
                    .limit(request.getLimit()).offset(request.getOffset()).build();
        }
        List<Roles> roles = atomicRolesService.queryList(request);
        List<RolesBean> rolesBeanList = roles.stream().map(this::buildRolesBean).collect(Collectors.toList());
        return PageResponse.<RolesBean>builder().data(rolesBeanList)
                .limit(request.getLimit()).offset(request.getOffset()).build();
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
        //roles.setName(request.getName());
        roles.setDesc(request.getDescription());
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
        //rolesBean.setName(roles.getName());
        rolesBean.setEnabled(roles.getEnabled());
        rolesBean.setDescription(roles.getDesc());
        rolesBean.setCreateTime(roles.getCreateTime().getTime());
        rolesBean.setUpdateTime(roles.getUpdateTime().getTime());
        return rolesBean;
    }
}