package top.easyblog.core.processor.push.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.easyblog.core.processor.push.MessageSendProcessor;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.BusinessMessageRecordContext;
import top.easyblog.support.event.MessageSendSuccessEvent;

@Slf4j
public class AbstractMessageSendProcessor implements MessageSendProcessor {

    @Autowired
    private ExecutorService messageProcessor;

    private static final int DEFAULT_SEND_WAIT_TIME = 60;
    private static final TimeUnit DEAULT_TIME_UNIT = TimeUnit.SECONDS;

    @Override
    public boolean send(BusinessMessageRecordContext message) {
        boolean sendSuccess = false;
        Future<Boolean> sendResultFuture = messageProcessor.submit(() -> doSend(message));
        try {
            sendSuccess = Optional.ofNullable(sendResultFuture.get(DEFAULT_SEND_WAIT_TIME, DEAULT_TIME_UNIT))
                    .orElse(false);
        } catch (Exception e) {
            log.info("Send message failed with exception", e);
        }
        return sendSuccess;
    }

    @Override
    public void asyncSend(BusinessMessageRecordContext message) {
        messageProcessor.execute(() -> doSend(message));
    }

    protected boolean doSend(BusinessMessageRecordContext message) {
       throw new UnsupportedOperationException("Method 'doSend' must implement before use");
    }

}
