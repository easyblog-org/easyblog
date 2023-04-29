package top.easyblog.support.event;

import lombok.Getter;
import top.easyblog.support.context.MessageConfigContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 20:50
 */
@Getter
public class MessageSendFailedEvent extends EasyApplicationContextEvent {

    private final MessageConfigContext context;

    private final Exception exception;

    public MessageSendFailedEvent(MessageConfigContext context, Exception exception) {
        super(context);
        this.context = context;
        this.exception = exception;
    }
}
