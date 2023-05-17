package top.easyblog.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicAccountService;
import top.easyblog.dao.auto.model.Account;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:33
 */
@Service
public class AccountService {

    @Autowired
    private AtomicAccountService atomicAccountService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 创建账号
     *
     * @param request
     * @return
     */
    public Account createAccount(CreateAccountRequest request) {
        Account account = null;
        if (Objects.isNull(request.getCreateDirect()) || Boolean.FALSE.equals(request.getCreateDirect())) {
            QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                    .identityType(request.getIdentityType())
                    .identifier(request.getIdentifier()).build();
            account = atomicAccountService.queryAccountByRequest(queryAccountRequest);
            if (Objects.nonNull(account)) {
                throw new BusinessException(EasyResultCode.USER_ACCOUNT_EXISTS);
            }
        }

        account = beanMapper.convertAccountCreateReq2Account(request);
        atomicAccountService.insertSelective(account);
        return account;
    }

    /**
     * 查询账号详情
     *
     * @param request
     * @return
     */
    public AccountBean queryAccountDetails(QueryAccountRequest request) {
        Account account = atomicAccountService.queryAccountByRequest(request);
        return Optional.ofNullable(account)
                .map(item -> beanMapper.convertAccount2AccountBean(item)).orElse(null);
    }


    /**
     * 不分页查询账号
     *
     * @param request
     * @return
     */
    public List<AccountBean> queryListUnlimited(QueryAccountListRequest request) {
        // 不分页查询，这里去除分页默认值，查询所有符合的数据
        request.setLimit(null);
        request.setOffset(null);
        return atomicAccountService.queryAccountListByRequest(request).stream()
                .map(item -> beanMapper.convertAccount2AccountBean(item)).collect(Collectors.toList());
    }

    /**
     * 分页查询账号
     *
     * @param request
     * @return
     */
    public PageResponse<AccountBean> queryAccountListPage(QueryAccountListRequest request) {
        long amount = atomicAccountService.countByRequest(request);
        if (amount == 0) {
            return PageResponse.<AccountBean>builder().limit(request.getLimit()).offset(request.getOffset())
                    .total(amount).data(Collections.emptyList()).build();
        }
        List<Account> accounts = atomicAccountService.queryAccountListByRequest(request);
        List<AccountBean> accountBeans = accounts.stream().map(item -> beanMapper.convertAccount2AccountBean(item)).collect(Collectors.toList());
        return PageResponse.<AccountBean>builder().limit(request.getLimit()).offset(request.getOffset())
                .total(amount).data(accountBeans).build();
    }

    /**
     * 根据用户Code+账号标识更新账号信息
     *
     * @param userCode
     * @param identityType
     * @param request
     */
    public void updateByIdentityType(String userCode, Integer identityType, UpdateAccountRequest request) {
        Account oldAccount = atomicAccountService.queryAccountByRequest(QueryAccountRequest.builder()
                .userCode(userCode).identityType(identityType).build());
        if (Objects.isNull(oldAccount)) {
            throw new BusinessException(EasyResultCode.ACCOUNT_NOT_FOUND);
        }

        Account account = beanMapper.convertAccountUpdateReq2Account(oldAccount.getId(), request);
        atomicAccountService.updateAccountByPKSelective(account);
    }

    /**
     * 根据账号Code更新账号信息
     *
     * @param code
     * @param request
     */
    public void updateAccount(String code, UpdateAccountRequest request) {
        Account oldAccount = atomicAccountService.queryAccountByRequest(QueryAccountRequest.builder().code(code).build());
        if (Objects.isNull(oldAccount)) {
            throw new BusinessException(EasyResultCode.ACCOUNT_NOT_FOUND);
        }

        Account account = beanMapper.convertAccountUpdateReq2Account(oldAccount.getId(), request);
        atomicAccountService.updateAccountByPKSelective(account);
    }

}
