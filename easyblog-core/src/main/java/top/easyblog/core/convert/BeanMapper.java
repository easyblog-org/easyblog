package top.easyblog.core.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.bean.MobileAreBean;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;
import top.easyblog.common.request.loginlog.UpdateSignInLogRequest;
import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.dao.auto.model.Account;
import top.easyblog.dao.auto.model.LoginLog;
import top.easyblog.dao.auto.model.MobileAreaCode;

/**
 * @author: frank.huang
 * @date: 2023-04-22 15:53
 */
@Mapper(componentModel = "spring")
public interface BeanMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    Account convertAccountCreateReq2Account(CreateAccountRequest request);


    AccountBean convertAccount2AccountBean(Account account);

    @Mapping(target = "userCode", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", source = "accountId")
    Account convertAccountUpdateReq2Account(Long accountId, UpdateAccountRequest request);


    @Mapping(target = "ipAddress", source = "ip")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    LoginLog convertLoginLogCreateReq2Account(CreateLoginLogRequest request);


    LoginLogBean convertLoginLog2LoginLogBean(LoginLog loginLog);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "accountCode", ignore = true)
    LoginLog convertLoginLogUpdateReq2LoginLog(Long id, UpdateSignInLogRequest request);


    MobileAreaCode convertMobileAreaCodeCreateReq2MobileArea(CreateMobileAreaRequest request);


    MobileAreBean convertMobileArea2MobileAreaBean(MobileAreaCode item);


    MobileAreaCode convertMobileAreaUpdateReq2MobileArea(Long id, UpdateMobileAreaRequest request);
}
