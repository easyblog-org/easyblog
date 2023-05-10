package top.easyblog.common.request.message.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-05 14:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMessageConfigsRequest {
    private List<String> codes;
    private List<String> types;
    private Boolean deleted;
    @Builder.Default
    @Min(value = 0, message = "limit can not less than 0")
    private Integer offset = 0;
    @Builder.Default
    @Max(value = 100, message = "limit can not bigger than 100")
    private Integer limit = 10;
}
