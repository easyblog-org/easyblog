package top.easyblog.common.bean;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2023-02-11 15:54
 */
@Data
public class MessageSendResultBean {
    /**
     * 发送渠道
     */
    private Byte channel;
    /**
     * 发送方
     */
    private String sender;
    /**
     * 接受者
     */
    private String receiver;
    /**
     * 发送状态
     */
    private Byte status;
    /**
     * 失败原因
     */
    private String failedReason;
}
