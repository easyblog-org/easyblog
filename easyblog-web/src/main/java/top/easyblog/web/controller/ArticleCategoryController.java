package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.request.article.*;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.ArticleCategoryService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-06-25 21:39
 */
@RestController
@RequestMapping("/v1/article-category")
public class ArticleCategoryController {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @ResponseWrapper
    @PostMapping
    public Object saveArticleCategory(@Valid @RequestBody CreateArticleCategoryRequest request) {
        return articleCategoryService.saveArticleCategory(request);
    }

    @ResponseWrapper
    @PutMapping("/{id}")
    public void updateArticleCategory(@PathVariable("id") Long id, @Valid @RequestBody UpdateArticleCategoryRequest request) {
        articleCategoryService.updateArticleCategory(id, request);
    }

    @ResponseWrapper
    @GetMapping("/{id}")
    public ArticleCategoryBean details(@PathVariable("id") Long id) {
        return articleCategoryService.details(id);
    }

    @ResponseWrapper
    @GetMapping
    public PageResponse<ArticleCategoryBean> list(@RequestParamAlias QueryArticleCategoryListRequest request) {
        return articleCategoryService.list(request);
    }
}
