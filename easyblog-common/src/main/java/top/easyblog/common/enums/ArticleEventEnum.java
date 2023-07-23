package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 文章详情页事件
 *
 * @author frank.huang
 * @date 2022/01/30 09:37
 */
@Getter
@AllArgsConstructor
public enum ArticleEventEnum {
    /**
     * 访问页面
     */
    PAGE_VIEWS,
    /**
     * 点赞
     */
    LIKES,
    /**
     * 收藏
     */
    FAVORITES,
    /**
     * 转发/分享
     */
    SHARE,
    /**
     * 举报
     */
    REPORT;

    public static ArticleEventEnum nameOf(String event) {
        if (StringUtils.isBlank(event)) {
            return null;
        }
        return Arrays.stream(values()).filter(item -> StringUtils.equalsIgnoreCase(item.name(), event))
                .findFirst().orElse(null);
    }
}
