package top.easyblog.core;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.response.EasyResultCode;

@Slf4j
@Service
public class UserRoleRelationshipService {

    @Autowired
    private RolesService rolesService;


    public void createOrRefreshUserRole(CreateOrRefreshUserRoleContext context) {
        List<String> roles = context.getRoles();
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
                .userIds(Collections.singletonList(context.getUserId().intValue())).build());
        if (userRoleNum > 0) {
            atomicUserRolesService.deleteByExample(UpdateUserRolesRequest.builder()
                    .userId(context.getUserId().intValue()).build());
        }

        roles.stream().filter(Objects::nonNull).forEach(roleCode -> {
            RolesBean rolesBean = rolesBeanMap.get(roleCode);
            UserRoleRelationship UserRoleRelationship = new UserRoleRelationship();
            UserRoleRelationship.setRoleId(Objects.requireNonNull(rolesBean, String.format("Role %s not found", roleCode)).getId().intValue());
            UserRoleRelationship.setUserId(context.getUserId().intValue());
            UserRoleRelationship.setEnabled(Boolean.TRUE);
            atomicUserRolesService.insertOne(UserRoleRelationship);
        });
    } 
    
}