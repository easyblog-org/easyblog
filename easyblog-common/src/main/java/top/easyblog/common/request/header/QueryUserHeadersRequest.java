package top.easyblog.common.request.header;

import lombok.*;

import java.util.List;

/**
 * @author frank.huang
 * @date 2022/02/02 09:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserHeadersRequest {
    private String code;
    private String userCode;
    private List<String> userCodes;
    private List<String> codes;
    private Byte status;
    private Integer limit;
    private Integer offset;
}
