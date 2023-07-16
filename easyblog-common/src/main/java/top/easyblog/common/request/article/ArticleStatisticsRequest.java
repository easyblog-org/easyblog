package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-07-16 16:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleStatisticsRequest {
    /**
     * 统计指标名称
     */
    private String statisticIndexName;
    /**
     * 增量
     */
    private int increment;
    /**
     * 指标主体code
      */
    private String code;

}
