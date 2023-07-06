package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryArticlesRequest {
    private String title;

    private List<String> status;

    private Boolean isTop;

    private List<String> codes;

    private String authorId;

    private String sections;

    @Builder.Default
    private Integer limit = 10;

    @Builder.Default
    private Integer offset = 0;

    private String orderCause;

    private String orderDir;

    private Long createTimeBegin;

    private Long createTimeEnd;
}
