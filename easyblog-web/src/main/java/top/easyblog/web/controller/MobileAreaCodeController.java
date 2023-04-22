package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaListRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.core.MobileAreaCodeService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:51
 */
@RestController
@RequestMapping("/v1/in/area-code")
public class MobileAreaCodeController {

    @Autowired
    private MobileAreaCodeService mobileAreaCodeService;


    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryMobileAreaRequest request) {
        return mobileAreaCodeService.queryMobileAreaDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void update(@PathVariable("code") String code,
                       @RequestBody @Valid UpdateMobileAreaRequest request) {
        mobileAreaCodeService.updateMobileArea(code, request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid @RequestParamAlias QueryMobileAreaListRequest request) {
        return mobileAreaCodeService.queryMobileAreaPage(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateMobileAreaRequest request) {
        mobileAreaCodeService.createMobileArea(request);
    }

    @ResponseWrapper
    @DeleteMapping
    public void deleteByIds(@RequestParam("phone_area_code_codes") List<String> phoneAreaCodeIds,
                            @RequestParam("token") String password) {
        mobileAreaCodeService.deleteByIds(phoneAreaCodeIds, password);
    }

}
