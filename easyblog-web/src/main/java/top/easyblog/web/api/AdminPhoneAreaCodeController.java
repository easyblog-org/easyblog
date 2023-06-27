package top.easyblog.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaListRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.platform.service.AdminPhoneAreaService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-03-12 17:51
 */
@RequestMapping("/v1/phone-area")
@RestController
public class AdminPhoneAreaCodeController {

    @Autowired
    private AdminPhoneAreaService phoneAreaService;

    @ResponseWrapper
    @PostMapping("")
    public void create(@RequestBody CreateMobileAreaRequest request) {
        phoneAreaService.create(request);
    }

    @ResponseWrapper
    @PutMapping("/{phoneAreaCodeId}")
    public void update(@PathVariable String phoneAreaCodeId, @RequestBody UpdateMobileAreaRequest request) {
        phoneAreaService.update(phoneAreaCodeId, request);
    }

    @ResponseWrapper
    @GetMapping("/tree")
    public Object queryPhoneAreaCodeMap(@RequestParamAlias QueryMobileAreaListRequest request) {
        return phoneAreaService.queryPhoneAreaCodeMap(request);
    }

    @ResponseWrapper
    @DeleteMapping("")
    public void deleteByIds(@RequestParam("phone_area_code_ids") List<String> phoneAreaCodeIds,
                            @RequestParam("password") String password) {
        phoneAreaService.deleteByIds(phoneAreaCodeIds, password);
    }

}
