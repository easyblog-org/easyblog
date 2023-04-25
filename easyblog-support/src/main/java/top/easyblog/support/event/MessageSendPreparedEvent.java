package top.easyblog.support.event;

import top.easyblog.support.context.BusinessMessageRecordContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 20:50
 */
public class MessageSendPreparedEvent extends EasyApplicationContextEvent {

    private BusinessMessageRecordContext message;

    public MessageSendPreparedEvent(BusinessMessageRecordContext message) {
        super(message);
        this.message = message;
    }
}
