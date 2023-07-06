package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.UserAvatarBean;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.core.UserAvatarService;

@Service
public class AdminUserAvatarService {

    @Autowired
    private UserAvatarService userAvatarService;

    public void create(CreateUserHeaderRequest request) {
        userAvatarService.createUserHeader(request);
    }

    public UserAvatarBean details(QueryUserHeaderImgRequest request) {
        return userAvatarService.queryUserHeaderDetails(request);
    }

}
