package top.easyblog.common.request.loginlog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author frank.huang
 * @date 2022/01/30 14:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryLoginLogListRequest {
    private String code;

    private List<String> codes;

    private String userCode;

    private List<String> userCodes;

    private String accountCode;

    private Integer status;

    private List<Integer> statuses;

    @Builder.Default
    private Integer offset = 0;

    @Builder.Default
    private Integer limit = 10;
}
