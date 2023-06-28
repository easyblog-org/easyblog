package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.UserAvatarBean;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.common.request.header.QueryUserHeadersRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.UserAvatarService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:42
 */
@RestController
@RequestMapping("/v1/in/header-img")
public class UserHeaderController {

    @Autowired
    private UserAvatarService headerImgService;

    @ResponseWrapper
    @PostMapping
    public void save(@Valid @RequestBody CreateUserHeaderRequest request) {
        headerImgService.createUserHeader(request);
    }

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryUserHeaderImgRequest request) {
        return headerImgService.queryUserHeaderDetails(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<UserAvatarBean> queryList(@Valid @RequestParamAlias QueryUserHeadersRequest request) {
        return headerImgService.queryUserHeaderList(request);
    }
}
