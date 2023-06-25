package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleCategoryRequest {
    private Long pid;

    @NotBlank(message = "Required param 'name' is not present.")
    private String name;
}
