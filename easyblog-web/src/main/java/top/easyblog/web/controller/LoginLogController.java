package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogRequest;
import top.easyblog.core.UserLoginLogService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author frank.huang
 * @date 2022/02/03 18:21
 */
@RestController
@RequestMapping("/v1/in/sign-log")
public class LoginLogController {

    @Autowired
    private UserLoginLogService userSignInLogService;

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryLoginLogRequest request) {
        return userSignInLogService.queryLoginLogDetails(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid @RequestParamAlias QueryLoginLogListRequest request) {
        return userSignInLogService.queryLoginLogList(request);
    }

}
