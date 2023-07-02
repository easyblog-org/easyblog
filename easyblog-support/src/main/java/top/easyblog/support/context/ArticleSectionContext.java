package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.bean.UserDetailsBean;
import top.easyblog.common.bean.UserAvatarBean;

import java.util.List;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2023-06-25 21:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSectionContext {
    private Map<Long, List<ArticleCategoryBean>> articleCategoryBeanMap;
    private Map<String, UserDetailsBean> authorMap;
    private Map<String, UserAvatarBean> authorAvatarBeanMap;
}
