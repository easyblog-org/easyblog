package top.easyblog.core.strategy.push.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.QueryMessageSendRecordRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.dao.atomic.AtomicMessageSendRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.MessageSendContext;
import top.easyblog.support.event.MessageSendSuccessEvent;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
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
public class MimeEmailPushStrategy implements MessagePushStrategy {

    private final JavaMailSender mailSender;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${message.sender}")
    private String emailSender;

    @Override
    public byte getPushType() {
        return MessageSendChannel.ATTACHMENT_EMAIL.getCode();
    }

    @Override
    public void push(MessageSendContext context) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
           mimeMessageHelper.setFrom(emailSender);
           mimeMessageHelper.setTo(context.getReceiver());
           mimeMessageHelper.setSubject(context.getTitle());
            mimeMessageHelper.setText(context.getContent());
            List<File> attachments = context.getAttachments();
            // 添加附件（多个）
            if (CollectionUtils.isNotEmpty(attachments)) {
                for (File attachment : attachments) {
                    mimeMessageHelper.addAttachment(attachment.getName(), attachment);
                }
            }
            // 发送邮件
            mailSender.send(mimeMessage);
            applicationEventPublisher.publishEvent(new MessageSendSuccessEvent(context));
        } catch (Exception e) {
            log.info(e.getMessage());

        }
    }
}
