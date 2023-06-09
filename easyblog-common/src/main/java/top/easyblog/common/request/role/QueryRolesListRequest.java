package top.easyblog.common.request.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRolesListRequest {
    private List<Long> ids;
    private List<String> codes;
    private List<String> names;
    //模糊查询
    private String name;
    private Boolean enabled;
    @Builder.Default
    private Integer limit = 10;
    @Builder.Default
    private Integer offset = 0;
}
