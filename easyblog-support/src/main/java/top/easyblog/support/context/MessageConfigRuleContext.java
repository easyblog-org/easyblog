package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-06 20:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageConfigRuleContext {
    private Byte channel;

    private List<String> configIds;

    private String templateCode;

    /**
     * 参数为空是否做校验
     */
    private boolean checkIfNonNull;
}
