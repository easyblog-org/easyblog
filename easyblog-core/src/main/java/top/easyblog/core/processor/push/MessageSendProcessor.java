package top.easyblog.core.processor.push;


import top.easyblog.support.context.BusinessMessageRecordContext;

/**
 * 消息推送处理接口，用于消息格式校验、解析组装和发送

 */
public interface MessageSendProcessor {
    
    /**
     * 同步发送消息
     * @param message 消息内容
     * @return 是否正确发送到目标服务器
     */
    boolean send(BusinessMessageRecordContext message);



   /**
    * 异步发送消息
    * @param message 消息内容
    */
    void asyncSend(BusinessMessageRecordContext message);
}
