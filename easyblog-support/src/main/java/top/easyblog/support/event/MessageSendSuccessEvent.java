package top.easyblog.support.event;

import lombok.Getter;
import top.easyblog.support.context.MessageSendContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 20:50
 */
@Getter
public class MessageSendSuccessEvent extends EasyApplicationContextEvent {

    private final MessageSendContext context;

    public MessageSendSuccessEvent( MessageSendContext context) {
        super(context);
        this.context = context;
    }
}
