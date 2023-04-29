package top.easyblog.core.strategy.push;

import top.easyblog.support.context.MessageSendContext;

import javax.mail.MessagingException;

/**
 * 消息发送策略
 *
 * @author: frank.huang
 * @date: 2023-02-11 20:06
 */
public interface MessagePushStrategy {

    /**
     * 获取发送策略
     *
     * @return
     */
    byte getPushType();

    /**
     * 发送消息
     *
     * @param context
     */
    void push(MessageSendContext context) throws MessagingException;

}
