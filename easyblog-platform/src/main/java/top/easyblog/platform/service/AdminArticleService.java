package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.request.article.*;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.ArticleCategoryService;
import top.easyblog.core.ArticleService;

/**
 * @author: frank.huang
 * @date: 2023-08-20 13:44
 */
@Service
public class AdminArticleService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;


    public String createArticle(CreateArticleRequest request) {
        return articleService.saveArticle(request);
    }


    public ArticleBean details(String code, String sections) {
        return articleService.details(code, sections);
    }


    public PageResponse<ArticleBean> list(QueryArticlesRequest request) {
        return articleService.list(request);
    }

    public void updateArticle(String code, UpdateArticleRequest request) {
        articleService.updateArticle(code, request);
    }

    public PageResponse<ArticleCategoryBean> queryArticleCategoryList(QueryArticleCategoryListRequest request) {
        return articleCategoryService.list(request);
    }

    public void createCategory(CreateArticleCategoryRequest request) {
        articleCategoryService.saveArticleCategory(request);
    }

    public void updateArticleCategory(Long id, UpdateArticleCategoryRequest request) {
        articleCategoryService.updateArticleCategory(id, request);
    }

    public void deleteCategory(Long id, String password) {
        articleCategoryService.deleteByIds(id,password);
    }
}
