package top.easyblog.common.request.article;

import lombok.Data;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:52
 */
@Data
public class UpdateArticleRequest {
    private String content;

    private String title;

    private Boolean isTop;

    private String category;

    // 子 category
    private List<String> tags;

    /**
     * 文章状态
     * @see top.easyblog.common.enums.ArticleStatus
     */
    private String status;
}
