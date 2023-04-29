package top.easyblog.core.lisenter;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;
import top.easyblog.support.event.MessageSendFailedEvent;
import top.easyblog.support.event.MessageSendPreparedEvent;
import top.easyblog.support.event.MessageSendSuccessEvent;

/**
 * @author: frank.huang
 * @date: 2023-04-25 22:02
 */
@Component
public class MessagePushListener implements SmartApplicationListener {


    @Override
    public boolean supportsEventType(@NotNull Class<? extends ApplicationEvent> event) {
        return MessageSendPreparedEvent.class.isAssignableFrom(event) ||
                MessageSendFailedEvent.class.isAssignableFrom(event) ||
                MessageSendSuccessEvent.class.isAssignableFrom(event);

    }


    @Override
    public void onApplicationEvent(@NotNull ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof MessageSendPreparedEvent) {
            onMessageSendPreparedEvent((MessageSendPreparedEvent)applicationEvent);
        } else if (applicationEvent instanceof MessageSendFailedEvent) {
            onMessageSendFailedEvent((MessageSendFailedEvent)applicationEvent);
        } else if (applicationEvent instanceof MessageSendSuccessEvent) {
            onMessageSendSuccessEvent((MessageSendSuccessEvent)applicationEvent);
        }
    }

    private void onMessageSendSuccessEvent(MessageSendSuccessEvent applicationEvent) {

    }

    private void onMessageSendFailedEvent(MessageSendFailedEvent applicationEvent) {

    }

    private void onMessageSendPreparedEvent(MessageSendPreparedEvent applicationEvent) {

    }
}
