package top.easyblog.support.event;

import lombok.Getter;
import top.easyblog.support.context.BusinessMessageRecordContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 20:50
 */
@Getter
public class MessageSendPreparedEvent extends EasyApplicationContextEvent {

    private final BusinessMessageRecordContext message;

    public MessageSendPreparedEvent(BusinessMessageRecordContext message) {
        super(message);
        this.message = message;
    }
}
