package top.easyblog.core;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.request.article.CreateArticleCategoryRequest;
import top.easyblog.common.request.article.QueryArticleCategoryListRequest;
import top.easyblog.common.request.article.UpdateArticleCategoryRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicArticleCategoryService;
import top.easyblog.dao.auto.model.ArticleCategory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:39
 */
@Slf4j
@Service
public class ArticleCategoryService {

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

}
