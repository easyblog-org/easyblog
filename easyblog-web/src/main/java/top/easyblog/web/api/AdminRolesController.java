package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.request.role.CreateRolesRequest;
import top.easyblog.common.request.role.QueryRolesDetailsRequest;
import top.easyblog.common.request.role.QueryRolesListRequest;
import top.easyblog.common.request.role.UpdateRolesRequest;
import top.easyblog.platform.service.AdminRolesService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-25 16:27
 */
@RequestMapping("/v1/roles")
@RestController
public class AdminRolesController {

    @Autowired
    private AdminRolesService userRolesService;

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryRolesDetailsRequest request) {
        return userRolesService.details(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void update(@PathVariable("code") String code,
            @RequestBody @Valid UpdateRolesRequest request) {
        userRolesService.updateRoles(code, request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryListPage(@RequestParamAlias QueryRolesListRequest request) {
        return userRolesService.queryRolesList(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateRolesRequest request) {
        userRolesService.createRoles(request);
    }

    @ResponseWrapper
    @GetMapping("/all")
    public Object queryAllRole() {
        return userRolesService.queryAllRoles();
    }
}
