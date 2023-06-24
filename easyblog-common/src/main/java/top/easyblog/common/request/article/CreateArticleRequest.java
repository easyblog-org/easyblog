package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import top.easyblog.common.enums.ArticleStatus;
import top.easyblog.common.request.BaseRequest;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest implements BaseRequest {
    //user_code
    @NotBlank(message = "Required param 'author_id' is not present.")
    private String authorId;

    @NotBlank(message = "Required param 'title' is not present.")
    private String title;

    @NotBlank(message = "Required param 'content' is not present.")
    private String content;

    @Builder.Default
    private Boolean isTop = Boolean.FALSE;

    @NotBlank(message = "Required param 'category' is not present.")
    private String category;

    /**
     * 文章状态
     *
     * @see top.easyblog.common.enums.ArticleStatus
     */
    private String status;

    private String featuredImage;

    // 子 category
    private List<String> tags;

    private void init() {
        if (StringUtils.isBlank(this.status)) {
            this.status = ArticleStatus.DRAFT.name();
        }
    }

    @Override
    public boolean validate() {
        init();
        return true;
    }
}
