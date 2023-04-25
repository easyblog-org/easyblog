package top.easyblog.support.event;

import top.easyblog.support.context.MessageConfigContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 20:50
 */
public class MessageSendFailedEvent extends EasyApplicationContextEvent {

    private MessageConfigContext context;

    public MessageSendFailedEvent(MessageConfigContext context) {
        super(context);
        this.context = context;
    }
}
