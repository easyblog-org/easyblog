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
public class CreateArticleEventRequest {
    @NotBlank(message = "Required param 'articleCode' is not present.")
    private String articleCode;
    @NotBlank(message = "Required param 'userCode' is not present.")
    private String userCode;
    @NotBlank(message = "Required param 'event' is not present.")
    private String event;
    private String operator;
    private String remark;
}
