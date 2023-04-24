package top.easyblog.core.strategy.verify;

import top.easyblog.support.context.MessageProcessorContext;

/**
 * @author: frank.huang
 * @date: 2023-02-12 14:22
 */
public interface MessageVerifyStrategy {
    /**
     * 获取发送策略
     *
     * @return
     */
    byte getPushType();

    /**
     * 校验消息合法
     *
     * @param context
     */
    void verify(MessageProcessorContext context);
}
