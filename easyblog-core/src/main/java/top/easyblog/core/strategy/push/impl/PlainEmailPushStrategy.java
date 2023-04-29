package top.easyblog.core.strategy.push.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.support.context.MessageSendContext;
import top.easyblog.support.event.MessageSendSuccessEvent;

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

    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${message.sender}")
    private String emailSender;

    @Override
    public byte getPushType() {
        return MessageSendChannel.PLAIN_EMAIL.getCode();
    }

    @Override
    public void push(MessageSendContext context) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(context.getTitle());
            simpleMailMessage.setFrom(emailSender);
            simpleMailMessage.setTo(context.getReceiver());
            simpleMailMessage.setText(context.getContent());
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

}
