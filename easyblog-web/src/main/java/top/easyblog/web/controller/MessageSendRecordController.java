package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.BusinessMessageRecordBean;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordsRequest;
import top.easyblog.common.request.message.record.UpdateBusinessMessageRecordRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-12 13:47
 */
@RequestMapping("/v1/in/message")
@RestController
public class MessageSendRecordController {

    @Autowired
    private BusinessMessageRecordService messageSendRecordService;


    @ResponseWrapper
    @PostMapping("/record")
    public BusinessMessageRecord createBusinessRecord(@RequestBody @Valid CreateBusinessMessageRecordRequest request) {
        return messageSendRecordService.createMessageRecord(request);
    }

    @ResponseWrapper
    @PutMapping("/record/{id}")
    public void updateBusinessRecord(@PathVariable("id") Long id,@RequestBody @Valid UpdateBusinessMessageRecordRequest request) {
        messageSendRecordService.updateMessageRecord(id, request);
    }

    @ResponseWrapper
    @GetMapping("/record")
    public BusinessMessageRecordBean details(@RequestParamAlias QueryBusinessMessageRecordRequest request) {
        return messageSendRecordService.details(request);
    }

    @ResponseWrapper
    @GetMapping("/records")
    public PageResponse<BusinessMessageRecordBean> list(@RequestParamAlias QueryBusinessMessageRecordsRequest request) {
        return messageSendRecordService.list(request);
    }


}
