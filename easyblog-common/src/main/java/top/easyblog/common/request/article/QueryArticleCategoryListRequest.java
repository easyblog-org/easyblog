package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryArticleCategoryListRequest {

    private List<Long> ids;

    private List<Long> pids;

    //根据名字模糊搜索
    private String name;

    //根据名字精确批量搜索
    private List<String> names;

    @Builder.Default
    private Integer offset = 0;

    @Builder.Default
    private Integer limit = 10;
}
