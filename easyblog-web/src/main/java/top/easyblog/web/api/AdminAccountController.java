package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.platform.service.AdminAccountService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-25 16:28
 */
@RequestMapping("/v1/account")
@RestController
public class AdminAccountController {

    @Autowired
    private AdminAccountService accountService;

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateAccountRequest request) {
        accountService.createAccount(request);
    }

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryAccountRequest request) {
        return accountService.queryAccountDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{account_code}")
    public void update(@PathVariable("account_code") String accountCode,
            @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateAccount(accountCode, request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid @RequestParamAlias QueryAccountListRequest request) {
        return accountService.queryAccountList(request);
    }

}
