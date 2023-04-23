package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.request.user.CreateUserRequest;
import top.easyblog.common.request.user.QueryUserListRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.request.user.UpdateUserRequest;
import top.easyblog.core.UserService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author frank.huang
 * @date 2022/02/03 18:20
 */
@RestController
@RequestMapping("/v1/in/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryUserRequest request) {
        return userService.queryUserDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public Long update(@PathVariable("code") String code,
                       @RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(code,request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@RequestParamAlias QueryUserListRequest request) {
        return userService.queryUserListPage(request);
    }

    @ResponseWrapper
    @PostMapping
    public UserDetailsBean create(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }
}
