package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author frank.huang
 * @date 2022/01/30 09:37
 */
@Getter
@AllArgsConstructor
public enum ArticleStatus {

    DRAFT("草稿", true),
    PUBLISHED("已发布文章", false),
    DELETING("删除中文章", false),
    DELETED("已删除文章", false);

    private final String desc;
    private final boolean defaultValue;

    public static ArticleStatus nameOf(String articleStatus) {
        if (StringUtils.isBlank(articleStatus)) return null;
        return Arrays.stream(ArticleStatus.values())
                .filter(item -> StringUtils.equalsIgnoreCase(item.name(), articleStatus)).findAny().orElse(null);
    }
}
