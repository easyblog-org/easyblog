package top.easyblog.common.request.loginlog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/01/30 13:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryLoginLogRequest {
    private String code;

    private String userCode;

    private String accountCode;

    private String token;

    private Integer status;
}
