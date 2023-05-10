package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-11 17:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageProcessorContext {
    /**
     * 接收人
     */
    private String receiver;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 模板Code
     */
    private String templateCode;
    /**
     * 模板解析后的可发送内容
     */
    private String content;
    /**
     * 标题
     */
    private String title;
    /**
     * 发送渠道
     */
    private Byte channel;
    /**
     * 发送参数 Json字符串
     */
    private String replaceValues;
    /**
     * 附件
     */
    private List<InputStream> attachments;

    private String messageRecordCode;

    private Long messageRecordId;

}
