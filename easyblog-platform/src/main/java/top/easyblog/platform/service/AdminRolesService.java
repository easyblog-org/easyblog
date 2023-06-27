package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.request.role.CreateRolesRequest;
import top.easyblog.common.request.role.QueryRolesDetailsRequest;
import top.easyblog.common.request.role.QueryRolesListRequest;
import top.easyblog.common.request.role.UpdateRolesRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.RolesService;

@Service
public class AdminRolesService {

    @Autowired
    private RolesService rolesService;

    /**
     * 查询角色详情
     * 
     * @param request
     * @return
     */
    public RolesBean details(QueryRolesDetailsRequest request) {
        return rolesService.details(request);
    }

    /**
     * 查询角色列表
     * 
     * @param request
     * @return
     */
    public PageResponse<RolesBean> queryRolesList(QueryRolesListRequest request) {
        return rolesService.queryRolesList(request);
    }

    /**
     * 查询全量角色
     * 
     * @return
     */
    public Object queryAllRoles() {
        return rolesService.queryAllRolesList();
    }

    /**
     * 创建新角色
     * 
     * @param request
     */
    public void createRoles(CreateRolesRequest request) {
        rolesService.create(request);
    }

    /**
     * 更新角色
     * 
     * @param code
     * @param request
     */
    public void updateRoles(String code, UpdateRolesRequest request) {
        rolesService.updateRoles(code, request);
    }

}
