package top.easyblog.web.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.easyblog.common.bean.UserAvatarBean;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.platform.service.AdminUserAvatarService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;


/**
 * @author: frank.huang
 * @date: 2023-03-11 15:30
 */
@RequestMapping("/v1/header-image")
@RestController
public class AdminUserAvatarContoller {

     @Autowired
    private AdminUserAvatarService headerImageService;

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateUserHeaderRequest request) {
        headerImageService.create(request);
    }

    @ResponseWrapper
    @GetMapping
    public UserAvatarBean details(@RequestParamAlias QueryUserHeaderImgRequest request) {
        return headerImageService.details(request);
    }
    
}
