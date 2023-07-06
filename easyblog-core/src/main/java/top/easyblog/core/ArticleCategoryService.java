package top.easyblog.core;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.QuerySection;
import top.easyblog.common.request.article.CreateArticleCategoryRequest;
import top.easyblog.common.request.article.QueryArticleCategoryListRequest;
import top.easyblog.common.request.article.UpdateArticleCategoryRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicArticleCategoryService;
import top.easyblog.dao.auto.model.ArticleCategory;
import top.easyblog.service.section.IArticleSectionInquireService;
import top.easyblog.support.context.ArticleSectionContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:39
 */
@Slf4j
@Service
public class ArticleCategoryService implements IArticleSectionInquireService {

    @Autowired
    private AtomicArticleCategoryService atomicArticleCategoryService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 创建文章分类
     *
     * @param request
     */
    public Long saveArticleCategory(CreateArticleCategoryRequest request) {
        checkPidValid(request.getPid());
        ArticleCategory newArticleCategory = beanMapper.convertArticleCategoryCreateReq2AeticleCategory(request);
        atomicArticleCategoryService.insertOne(newArticleCategory);
        return newArticleCategory.getId();
    }

    private void checkPidValid(Long pid) {
        if (Objects.isNull(pid)) {
            log.info("Pid is null,will do not check parent category valid.");
            return;
        }
        ArticleCategoryBean articleCategoryBean = details(pid);
        Assert.notNull(articleCategoryBean, "Article parent category of pid=" + pid + " not found");
    }


    /**
     * 更新文章分类
     *
     * @param id
     * @param request
     */
    public void updateArticleCategory(Long id, UpdateArticleCategoryRequest request) {
        ArticleCategoryBean articleCategoryBean = details(id);
        Assert.notNull(articleCategoryBean, "Article category of id=" + id + " not found");
        checkPidValid(request.getPid());
        ArticleCategory newArticleCategory = beanMapper.convertArticleCategoryUpdateReq2AeticleCategory(articleCategoryBean.getId(), request);
        atomicArticleCategoryService.updateByPrimaryKeySelective(newArticleCategory);
    }

    /**
     * 查询文章分类详情
     *
     * @param id
     * @return
     */
    public ArticleCategoryBean details(Long id) {
        if (Objects.isNull(id)) return null;
        List<ArticleCategory> articleCategories = atomicArticleCategoryService.queryListByRequest(QueryArticleCategoryListRequest.builder()
                .ids(Collections.singletonList(id)).build());
        return CollectionUtils.isEmpty(articleCategories) ? null : beanMapper.convertArticleCategory2ArticleCategoryBean(Iterables.getFirst(articleCategories, null));
    }


    /**
     * 查询文章分类列表
     *
     * @param request
     * @return
     */
    public PageResponse<ArticleCategoryBean> list(QueryArticleCategoryListRequest request) {
        long hits = atomicArticleCategoryService.countByRequest(request);
        PageResponse<ArticleCategoryBean> pageResponse = PageResponse.<ArticleCategoryBean>builder()
                .total(hits)
                .limit(request.getLimit())
                .offset(request.getOffset())
                .data(Collections.emptyList()).build();
        if (Objects.equals(NumberUtils.LONG_ZERO, hits)) {
            return pageResponse;
        }

        List<ArticleCategory> articleCategories = atomicArticleCategoryService.queryListByRequest(request);
        List<ArticleCategoryBean> articleCategoryBeans = articleCategories.stream()
                .map(item -> beanMapper.convertArticleCategory2ArticleCategoryBean(item))
                .collect(Collectors.toList());
        pageResponse.setData(articleCategoryBeans);
        return pageResponse;
    }


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
        List<Long> categoryIds = articleBeanList.stream()
                .map(item -> Arrays.stream(StringUtils.split(item.getCategoryIds(), Constants.COMMA)).map(Long::parseLong)
                        .collect(Collectors.toList())).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        if (StringUtils.containsIgnoreCase(QuerySection.QUERY_ARTICLE_CATEGORY.name(), section) || queryWhenSectionEmpty) {
            List<ArticleCategory> articleCategories = atomicArticleCategoryService.queryListByRequest(QueryArticleCategoryListRequest.builder()
                    .ids(categoryIds).limit(null).offset(null).build());
            Map<Long, List<ArticleCategoryBean>> articleCategoryBeanMap = articleCategories.stream().filter(Objects::nonNull)
                    .map(item -> beanMapper.convertArticleCategory2ArticleCategoryBean(item))
                    .collect(Collectors.groupingBy(ArticleCategoryBean::getId));
            ctx.setArticleCategoryBeanMap(articleCategoryBeanMap);
        }
    }
}
