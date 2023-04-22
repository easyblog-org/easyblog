package top.easyblog.common.request.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author frank.huang
 * @date 2022/02/06 09:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryAccountListRequest {
    private List<String> userCodes;
    private Integer status;
    private String identifier;
    private Integer identityType;
    private Integer verified;
    @Builder.Default
    private Integer limit = 10;
    @Builder.Default
    private Integer offset = 0;
}
