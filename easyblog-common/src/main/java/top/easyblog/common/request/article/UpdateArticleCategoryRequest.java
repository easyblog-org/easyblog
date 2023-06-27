package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleCategoryRequest {
    private Long pid;

    private String name;
}
