package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.request.message.record.CreateMessageSendRecordRequest;
import top.easyblog.support.context.MessageProcessorContext;

/**
 * 消息发送服务
 *
 * @author: frank.huang
 * @date: 2023-02-12 12:33
 */
@Slf4j
@Service
public class MessageSendRecordService {

    @Autowired
    private MessageSendChainService messageSendChainService;


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
