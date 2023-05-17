package top.easyblog.core.lisenter;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.MessageSendStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.UpdateBusinessMessageRecordRequest;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.core.processor.push.MessagePushProcessor;
import top.easyblog.support.context.BusinessMessageRecordContext;
import top.easyblog.support.context.MessageConfigContext;
import top.easyblog.support.context.MessageSendContext;
import top.easyblog.support.event.MessageSendFailedEvent;
import top.easyblog.support.event.MessageSendPreparedEvent;
import top.easyblog.support.event.MessageSendSuccessEvent;

import java.util.Objects;
import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2023-04-25 22:02
 */
@Slf4j
@Component
public class MessagePushListener implements SmartApplicationListener {

    @Autowired
    private MessagePushProcessor messageSendProcessor;

    @Autowired
    private BusinessMessageRecordService businessMessageRecordService;

    @Override
    public boolean supportsEventType(@NotNull Class<? extends ApplicationEvent> event) {
        return MessageSendPreparedEvent.class.isAssignableFrom(event) ||
                MessageSendFailedEvent.class.isAssignableFrom(event) ||
                MessageSendSuccessEvent.class.isAssignableFrom(event);
    }

    @Async
    @Override
    public void onApplicationEvent(@NotNull ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof MessageSendPreparedEvent) {
            onMessageSendPreparedEvent((MessageSendPreparedEvent) applicationEvent);
        } else if (applicationEvent instanceof MessageSendFailedEvent) {
            onMessageSendFailedEvent((MessageSendFailedEvent) applicationEvent);
        } else if (applicationEvent instanceof MessageSendSuccessEvent) {
            onMessageSendSuccessEvent((MessageSendSuccessEvent) applicationEvent);
        }
    }

    private void onMessageSendSuccessEvent(MessageSendSuccessEvent applicationEvent) {
        MessageSendContext context = applicationEvent.getContext();
        MessageConfigContext messageConfigContext = null;
        if (Objects.isNull(context) || (Objects.isNull(messageConfigContext = context.getMessageConfigContext()))) {
            log.info("Message success context is null,ignore this message success event");
            return;
        }

        Long messageRecordId = messageConfigContext.getBusinessMessageRecordId();
        businessMessageRecordService.updateMessageRecord(messageRecordId, UpdateBusinessMessageRecordRequest.builder()
                .status(MessageSendStatus.SUCCESS.getCode()).build());
    }

    private void onMessageSendFailedEvent(MessageSendFailedEvent applicationEvent) {
        MessageConfigContext context = applicationEvent.getContext();
        if (Objects.isNull(context)) {
            log.info("Message send config context is null,ignore this message failed event");
            return;
        }

        String failReason = Optional.ofNullable(applicationEvent.getException()).map(e -> {
            if (e instanceof BusinessException) {
                return ((BusinessException) e).getCode();
            } else {
                return e.getMessage();
            }
        }).orElse("Unknown exception");

        businessMessageRecordService.updateMessageRecord(context.getBusinessMessageRecordId(),
                UpdateBusinessMessageRecordRequest.builder()
                        .status(MessageSendStatus.FAILED.getCode()).failReason(failReason).build());
    }

    private void onMessageSendPreparedEvent(MessageSendPreparedEvent applicationEvent) {
        BusinessMessageRecordContext context = applicationEvent.getMessage();
        if (Objects.isNull(context)) {
            log.info("Message record context is null,ignore this message send prepared event");
            return;
        }

        if (context.getIsSync()) {
            messageSendProcessor.asyncSend(context);
        } else {
            messageSendProcessor.send(context);
        }
    }
}
