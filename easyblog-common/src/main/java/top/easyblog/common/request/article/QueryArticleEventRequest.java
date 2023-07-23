package top.easyblog.common.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-07-23 11:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryArticleEventRequest {
    private List<String> articleCodes;
    private List<String> userCodes;
    private List<String> events;
    private List<String> operators;
}
