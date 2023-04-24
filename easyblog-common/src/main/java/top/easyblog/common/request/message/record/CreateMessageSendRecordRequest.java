package top.easyblog.common.request.message.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.InputStream;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-12 13:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageSendRecordRequest {
    /**
     * 接收人
     */
    @NotBlank(message = "Required param 'receiver' is not present.")
    private String receiver;
    /**
     * 发送人
     */
    @NotBlank(message = "Required param 'sender' is not present.")
    private String sender;
    /**
     * 标题
     */
    private String title;
    /**
     * 发送取值参数 Json字符串
     */
    private String replaceValues;
    /**
     * 模板
     */
    private String templateCode;
    /**
     * 附件 (for email)
     */
    private List<InputStream> attachments;
}
