package top.easyblog.core.strategy.push.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.QueryMessageSendRecordRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.dao.atomic.AtomicMessageSendRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.MessageSendContext;

import java.util.Objects;

/**
 * 纯文本邮件
 *
 * @author: frank.huang
 * @date: 2023-02-11 20:10
 */
@Slf4j
@Component
@AllArgsConstructor
public class PlainEmailPushStrategy implements MessagePushStrategy {

    private final JavaMailSender mailSender;

    private final AtomicMessageSendRecordService atomicMessageSendRecordService;

    @Override
    public byte getPushType() {
        return MessageSendChannel.PLAIN_EMAIL.getCode();
    }

    @Override
    public void push(MessageSendContext context) {
        BusinessMessageRecord messageSendRecord = atomicMessageSendRecordService.details(QueryMessageSendRecordRequest.builder()
                .id(context.getSendRecordId()).build());
        if (Objects.isNull(messageSendRecord)) {
            throw new BusinessException(EasyResultCode.SEND_RECORD_NOT_FOUND);
        }

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            /*simpleMailMessage.setSubject(messageSendRecord.getTitle());
            simpleMailMessage.setFrom(messageSendRecord.getSender());
            simpleMailMessage.setTo(messageSendRecord.getReceiver());
            simpleMailMessage.setText(messageSendRecord.getContent());*/
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessException(EasyResultCode.SEND_MESSAGE_FAILED);
        }
    }

}
