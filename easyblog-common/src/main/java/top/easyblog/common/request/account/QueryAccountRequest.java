package top.easyblog.common.request.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/01/30 13:37
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryAccountRequest {
    private String code;

    private String userCode;

    private Integer identityType;

    private String identifier;

    private String credential;

    private Integer status;
}
