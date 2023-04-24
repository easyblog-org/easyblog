package top.easyblog.common.request.message.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMessageConfigRuleRequest {
    private String code;
}
