package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.request.login.AdminLoginRequest;
import top.easyblog.common.request.login.AdminPasswordModifyRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.AdminLoginService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-02-25 13:52
 */
@RequestMapping("/admin/v1/auth")
@RestController
public class AdminLoginController {

    @Autowired
    private AdminLoginService loginService;

    @ResponseWrapper
    @PostMapping("/login")
    public AuthenticationDetailsBean login(@RequestBody AdminLoginRequest request) {
        return loginService.login(request);
    }

    @ResponseWrapper
    @PostMapping("/logout")
    public Boolean logout(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }

    @ResponseWrapper
    @GetMapping("/refresh")
    public AuthenticationDetailsBean refresh(@RequestParam("userCode") String userCode,
            @RequestParam("accountCode") String accountCode) {
        return loginService.refresh(userCode, accountCode);
    }

    @ResponseWrapper
    @PostMapping("/modify-pwd")
    public void modifyPassword(@RequestBody AdminPasswordModifyRequest request) {
        loginService.modifyPassword(request);
    }

    @ResponseWrapper
    @GetMapping("/logs")
    public PageResponse<LoginLogBean> queryLoginLogs(@RequestParamAlias QueryLoginLogListRequest request) {
        return loginService.queryLoginLogs(request);
    }
}
