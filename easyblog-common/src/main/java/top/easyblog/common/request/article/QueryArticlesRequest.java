package top.easyblog.common.request.article;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.request.BaseRequest;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryArticlesRequest implements BaseRequest {
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


    @Override
    public boolean validate() {
        if (StringUtils.isNotBlank(orderCause) &&
                !Constants.ARTICLE_LIST_ORDER_CAUSE.contains(orderCause.toLowerCase())) {
            return false;
        }
        return !StringUtils.isNotBlank(orderDir) || Constants.ORDER_DIRS.contains(orderDir.toLowerCase());
    }
}
