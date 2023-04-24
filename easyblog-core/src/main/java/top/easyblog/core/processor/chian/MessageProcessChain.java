package top.easyblog.core.processor.chian;


import top.easyblog.support.context.MessageProcessorContext;

/**
 * 消息发送处理器
 *
 * @author: frank.huang
 * @date: 2023-02-11 15:53
 */
public interface MessageProcessChain {

    /**
     * 处理器优先级
     *
     * @return
     */
    int priority();

    /**
     * 发送消息
     *
     * @param context 消息发送上下文
     * @return
     */
    MessageProcessorContext process(MessageProcessorContext context);

}
