package top.easyblog.common.request.user;

import lombok.*;

import java.util.List;

/**
 * @author frank.huang
 * @date 2022/02/03 18:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserListRequest {

    private List<String> codes;

    private Integer status;

    private Integer level;

    private String nickname;

    /**
     * 查询选项，accounts、sign_log 分别标识需要查询哪些数据
     */
    private String sections;

    @Builder.Default
    private Integer limit = 10;

    @Builder.Default
    private Integer offset = 0;

}
