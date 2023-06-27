package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.BusinessMessageRecordBean;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordsRequest;
import top.easyblog.common.request.message.record.UpdateBusinessMessageRecordRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.AdminMessagePushRecordService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-05-13 13:24
 */
@RestController
@RequestMapping("/v1/message")
public class AdminMessagePushRecordController {

    @Autowired
    private AdminMessagePushRecordService messagePushRecordService;


    @ResponseWrapper
    @PostMapping("")
    public void create(@RequestBody @Valid CreateBusinessMessageRecordRequest request) {
        messagePushRecordService.create(request);
    }


    @ResponseWrapper
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, UpdateBusinessMessageRecordRequest request) {
        messagePushRecordService.update(id, request);
    }


    @ResponseWrapper
    @GetMapping("")
    public BusinessMessageRecordBean details(@RequestParamAlias QueryBusinessMessageRecordRequest request) {
        return messagePushRecordService.details(request);
    }


    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<BusinessMessageRecordBean> list(@RequestParamAlias QueryBusinessMessageRecordsRequest request) {
        return messagePushRecordService.list(request);
    }

}
