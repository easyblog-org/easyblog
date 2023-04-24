package top.easyblog.core.strategy.verify.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.core.strategy.verify.MessageVerifyService;
import top.easyblog.core.strategy.verify.MessageVerifyStrategy;
import top.easyblog.core.strategy.verify.VerifyContext;
import top.easyblog.core.strategy.verify.impl.verifiers.AttachmentValidVerifier;
import top.easyblog.core.strategy.verify.impl.verifiers.TemplateValidVerifier;
import top.easyblog.support.context.MessageProcessorContext;

/**
 * 带附件的邮件参数校验
 *
 * @author: frank.huang
 * @date: 2023-02-12 14:30
 */
@Slf4j
@Component
public class MimeEmailVerifyStrategy implements MessageVerifyStrategy {

    @Autowired
    private MessageVerifyService messageVerifyService;

    @Override
    public byte getPushType() {
        return MessageSendChannel.PLAIN_EMAIL.getCode();
    }

    @Override
    public void verify(MessageProcessorContext context) {
        messageVerifyService.verify(VerifyContext.builder()
                .templateCode(context.getTemplateCode())
                .attachments(context.getAttachments())
                .checkOptions(Lists.newArrayList(
                        TemplateValidVerifier.class,
                        AttachmentValidVerifier.class
                ))
                .build());
    }
}
