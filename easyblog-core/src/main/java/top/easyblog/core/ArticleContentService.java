package top.easyblog.core;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleContentBean;
import top.easyblog.common.enums.QuerySection;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicArticleContentService;
import top.easyblog.dao.mongo.model.ArticleContent;
import top.easyblog.service.section.IArticleSectionInquireService;
import top.easyblog.support.context.ArticleSectionContext;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-07-08 16:47
 */
@Service
public class ArticleContentService implements IArticleSectionInquireService {

    @Autowired
    private AtomicArticleContentService atomicArticleContentService;

    @Autowired
    private BeanMapper beanMapper;


    /**
     * 聚合服务中进行选项查询
     *
     * @param section               选项名称
     * @param ctx                   上下文
     * @param articleBeanList       查询参数
     * @param queryWhenSectionEmpty 是否在选项名称为空时继续执行查询
     * @return
     */
    @Override
    public void execute(String section, ArticleSectionContext ctx, List<ArticleBean> articleBeanList, boolean queryWhenSectionEmpty) {
        if (CollectionUtils.isEmpty(articleBeanList)) return;
        List<String> contentIds = articleBeanList.stream().map(ArticleBean::getContentId).distinct().collect(Collectors.toList());
        if (StringUtils.containsIgnoreCase(QuerySection.QUERY_ARTICLE_CONTENT.name(), section) || queryWhenSectionEmpty) {
            List<ArticleContent> articleContents = atomicArticleContentService.queryListByIds(contentIds);
            Map<String, ArticleContentBean> articleContentMap = articleContents.stream().filter(Objects::nonNull)
                    .map(item -> beanMapper.convertArticleContent2Bean(item))
                    .collect(Collectors.toMap(ArticleContentBean::getContentId, Function.identity(), (e1, e2) -> e1));
            ctx.setArticleContentBeanMap(articleContentMap);
        }
    }

}
