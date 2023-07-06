package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.AccountService;

@Service
public class AdminAccountService {


    @Autowired
    private AccountService accountService;

    
    public void createAccount(CreateAccountRequest request) {
        accountService.createAccount(request);
    }

    public AccountBean queryAccountDetails(QueryAccountRequest request) {
        return accountService.queryAccountDetails(request);
    }

    public void updateAccount(String accountCode, UpdateAccountRequest request) {
        accountService.updateAccount(accountCode, request);
    }

    public PageResponse<AccountBean> queryAccountList(QueryAccountListRequest request) {
        return accountService.queryAccountListPage(request);
    }

}
