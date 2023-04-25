package top.easyblog.support.event;

import top.easyblog.support.context.MessageSendContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 20:50
 */
public class MessageSendSuccessEvent extends EasyApplicationContextEvent {

    private MessageSendContext context;

    public MessageSendSuccessEvent( MessageSendContext context) {
        super(context);
        this.context = context;
    }
}
