package top.easyblog.common.request.message.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMessageConfigRulesRequest {
    private List<Long> ids;

    private Boolean deleted;

    private List<String> businessEvents;

    private List<String> businessModules;

    @Builder.Default
    @Min(value = 0, message = "Param 'offset' can not less than 0")
    private Integer offset = 0;

    @Builder.Default
    @Max(value = 100, message = "Param 'limit' can not bigger than 100")
    private Integer limit = 10;
}
