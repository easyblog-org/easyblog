package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.request.message.record.CreateMessageSendRecordRequest;
import top.easyblog.dao.atomic.AtomicBusinessMessageRecordService;
import top.easyblog.support.context.MessageProcessorContext;

/**
 * 创建消息发送
 *
 * @author: frank.huang
 * @date: 2023-02-12 12:33
 */
@Slf4j
@Service
public class BusinessMessageRecordService {


    @Autowired
    private AtomicBusinessMessageRecordService atomicBusinessMessageRecordService;

    @Autowired
    private MessageSendChainService messageSendChainService;


    public void createMessageRecord(CreateMessageSendRecordRequest request) {


    }


    public void updateMessageRecord(){

    }


    public void details(){

    }


    public void list(){

    }

    public MessageProcessorContext sendPlainEmail(CreateMessageSendRecordRequest request) {
        return messageSendChainService.execute(MessageProcessorContext.builder()
                .receiver(request.getReceiver())
                .sender(request.getSender())
                .title(request.getTitle())
                .templateCode(request.getTemplateCode())
                .channel(MessageSendChannel.PLAIN_EMAIL.getCode())
                .replaceValues(request.getReplaceValues())
                .build());
    }

    public MessageProcessorContext sendAttachmentEmail(CreateMessageSendRecordRequest request) {
        return messageSendChainService.execute(MessageProcessorContext.builder()
                .receiver(request.getReceiver())
                .sender(request.getSender())
                .title(request.getTitle())
                .templateCode(request.getTemplateCode())
                .channel(MessageSendChannel.ATTACHMENT_EMAIL.getCode())
                .replaceValues(request.getReplaceValues())
                .attachments(request.getAttachments())
                .build());
    }

}
