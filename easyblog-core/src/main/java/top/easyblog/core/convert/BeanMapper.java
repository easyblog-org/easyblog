package top.easyblog.core.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.dao.auto.model.Account;
import top.easyblog.support.util.IdGenerator;

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

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", source = "accountId")
    Account convertUpdateAccount2Account(Long accountId, UpdateAccountRequest request);
}
