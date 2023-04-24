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
     * id 用于by主键更新
     */
    private Long sendRecordId;
    /**
     * Only for email
     */
    private List<File> attachments;
}
