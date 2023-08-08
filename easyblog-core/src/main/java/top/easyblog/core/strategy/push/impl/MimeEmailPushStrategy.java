package top.easyblog.core.strategy.push.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.support.context.MessageSendContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * 纯文本邮件
 *
 * @author: frank.huang
 * @date: 2023-02-11 20:10
 */
@Slf4j
@Component
public class MimeEmailPushStrategy implements MessagePushStrategy {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${custom.message.email.sender}")
    private String emailSender;

    @Override
    public byte getPushType() {
        return MessageSendChannel.ATTACHMENT_EMAIL.getCode();
    }

    @Override
    public void push(MessageSendContext context) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(emailSender);
        mimeMessageHelper.setTo(context.getReceiver());
        mimeMessageHelper.setSubject(context.getTitle());
        mimeMessageHelper.setText(context.getContent(),true);
        List<File> attachments = context.getAttachments();
        // 添加附件（多个）
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (File attachment : attachments) {
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            }
        }
        // 发送邮件
        mailSender.send(mimeMessage);
        log.info("Send html e-mail to {} success!", context.getReceiver());;
    }
}
