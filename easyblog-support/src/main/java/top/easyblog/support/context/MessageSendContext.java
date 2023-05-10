package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-11 20:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendContext {

    /**
     * 发送配置
     */
    private MessageConfigContext messageConfigContext;

    /**
     * 收件人
     */
    private String receiver;
    /**
     * 内容
     */
    private String content;
    /**
     * 主体（标题）
     */
    private String title;

    /**
     * 渠道
     */
    private Byte channel;

    /**
     * 附件（Only for email）
     */
    private List<File> attachments;
}
