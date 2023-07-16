package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleRequest {
    private String content;

    private String title;

    private Boolean isTop;

    private String category;

    // 子 category
    private List<String> tags;

    private Integer likesNum;

    private Integer favoritesNum;

    private Integer reportsNum;

    private Integer pageViews;

    /**
     * 文章状态
     * @see top.easyblog.common.enums.ArticleStatus
     */
    private String status;
}
