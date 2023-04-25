package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.CreateMessageSendRecordRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.support.context.MessageProcessorContext;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-02-12 13:47
 */
@RequestMapping("/v1/message/send")
@RestController
public class MessageSendRecordController {

    @Autowired
    private BusinessMessageRecordService messageSendRecordService;


 /*   @ResponseWrapper
    @PostMapping("/email")
    public MessageProcessorContext sendPlainEmail(@RequestBody @Valid CreateMessageSendRecordRequest request) {
        return messageSendRecordService.sendPlainEmail(request);
    }

    @ResponseWrapper
    @PostMapping("/attachment-email")
    public MessageProcessorContext sendAttachmentEmail(@RequestBody @Valid CreateMessageSendRecordRequest request,
                                                       @RequestParam("attachments") MultipartFile[] attachments) {
        List<InputStream> attachmentsList = Arrays.stream(attachments).map(multipartFile -> {
            try {
                return multipartFile.getInputStream();
            } catch (IOException e) {
                throw new BusinessException(EasyResultCode.SEND_MESSAGE_FAILED);
            }
        }).collect(Collectors.toList());
        request.setAttachments(attachmentsList);
        return messageSendRecordService.sendAttachmentEmail(request);
    }*/


}
