package top.easyblog.core.strategy.push.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.support.context.MessageSendContext;

/**
 * 纯文本邮件
 *
 * @author: frank.huang
 * @date: 2023-02-11 20:10
 */
@Slf4j
@Component
public class PlainEmailPushStrategy implements MessagePushStrategy {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Override
    public byte getPushType() {
        return MessageSendChannel.PLAIN_EMAIL.getCode();
    }

    @Override
    public void push(MessageSendContext context) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(context.getTitle());
        simpleMailMessage.setFrom(emailSender);
        simpleMailMessage.setTo(context.getReceiver());
        simpleMailMessage.setText(context.getContent());
        mailSender.send(simpleMailMessage);
        log.info("Send plain e-mail to {} success!", context.getReceiver());;
    }

}
