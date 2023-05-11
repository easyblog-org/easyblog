package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.bean.MessageConfigBean;

/**
 * @author: frank.huang
 * @date: 2023-05-11 20:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageParseContext {
    private String businessMessage;
    private MessageConfigBean config;
}
