package top.easyblog.core;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.role.QueryUserRolesListRequest;
import top.easyblog.common.request.role.UpdateUserRolesRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.dao.atomic.AtomicUserRolesService;
import top.easyblog.dao.auto.model.UserRoleRelationship;
import top.easyblog.support.context.CreateOrRefreshUserRoleContext;
import top.easyblog.support.event.UserCreateOrUpdateEvent;

@Slf4j
@Service
public class UserRoleRelationshipService {

    @Autowired
    private AtomicUserRolesService atomicUserRolesService;

    @Autowired
    private RolesService rolesService;


    /**
     * 更新User和Role的映射关系
     *
     * @param event
     */
    @Async
    @EventListener
    public void refreshUserRole(UserCreateOrUpdateEvent event) {
        CreateOrRefreshUserRoleContext context = Optional.ofNullable(event).map(UserCreateOrUpdateEvent::getContext).orElse(null);
        List<String> roles = Optional.ofNullable(context).map(CreateOrRefreshUserRoleContext::getRoles).orElse(null);
        if (CollectionUtils.isEmpty(roles)) {
            log.info("Empty role list.....ignore!");
            return;
        }

        List<RolesBean> rolesBeans = rolesService.queryAllRolesList();
        if (CollectionUtils.isEmpty(rolesBeans)) {
            throw new BusinessException(EasyResultCode.ROLE_NOT_FOUND);
        }

        Map<String, RolesBean> rolesBeanMap = rolesBeans.stream().collect(Collectors.toMap(RolesBean::getCode, Function.identity(), (x, y) -> x));

        // 存在 user-role 映射关系，删除老的
        long userRoleNum = atomicUserRolesService.countByRequest(QueryUserRolesListRequest.builder()
                .userIds(Collections.singletonList(context.getUserId())).build());
        if (userRoleNum > 0) {
            atomicUserRolesService.deleteByExample(UpdateUserRolesRequest.builder()
                    .userId(context.getUserId()).build());
        }

        roles.stream().filter(Objects::nonNull).forEach(roleCode -> {
            RolesBean rolesBean = rolesBeanMap.get(roleCode);
            UserRoleRelationship UserRoleRelationship = new UserRoleRelationship();
            UserRoleRelationship.setRoleId(Objects.requireNonNull(rolesBean, String.format("Role %s not found", roleCode)).getId());
            UserRoleRelationship.setUserId(context.getUserId());
            UserRoleRelationship.setEnabled(Boolean.TRUE);
            atomicUserRolesService.insertOne(UserRoleRelationship);
        });
    }

}