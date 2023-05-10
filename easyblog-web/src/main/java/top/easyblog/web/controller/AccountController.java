package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.AccountService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author frank.huang
 * @date 2022/02/06 10:32
 */
@RestController
@RequestMapping("/v1/in/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

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
    @PutMapping("/{code}")
    public void update(@PathVariable("code") String code,
                       @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateAccount(code, request);
    }

    @ResponseWrapper
    @PutMapping("/{user_code}/{identify_type}")
    public void update(@PathVariable("user_code") String userCode,
                       @PathVariable("identify_type") Integer identityType,
                       @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateByIdentityType(userCode, identityType, request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<AccountBean> queryList(@Valid @RequestParamAlias QueryAccountListRequest request) {
        return accountService.queryAccountListPage(request);
    }
}
