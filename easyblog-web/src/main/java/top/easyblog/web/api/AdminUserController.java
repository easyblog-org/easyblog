package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.request.user.CreateUserAccountRequest;
import top.easyblog.common.request.user.QueryUserListRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.request.user.UpdateUserAccountRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.AdminUserService;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-25 13:48
 */
@RequestMapping("/admin/v1/user")
@RestController
public class AdminUserController {

    @Autowired
    private AdminUserService userService;

    @ResponseWrapper
    @PostMapping("")
    public UserDetailsBean createUserAccount(@RequestBody @Valid CreateUserAccountRequest request) {
        return userService.createUserAccount(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void updateUserAccount(@PathVariable("code") String code,
            @RequestBody @Valid UpdateUserAccountRequest request) {
        userService.updateUserAccount(code, request);
    }

    @ResponseWrapper
    @GetMapping("")
    public UserDetailsBean details(QueryUserRequest request) {
        return userService.details(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<UserDetailsBean> queryList(QueryUserListRequest request) {
        return userService.queryUserList(request);
    }

}