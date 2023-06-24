package top.easyblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.request.article.CreateArticleRequest;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.ArticleService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:56
 */
@RestController
@RequestMapping("/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ResponseWrapper
    @PostMapping
    public Object saveArticle(@Valid @RequestBody CreateArticleRequest request) {
        return articleService.saveArticle(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void updateArticle(@PathVariable("code") String code, @Valid @RequestBody UpdateArticleRequest request) {
        articleService.updateArticle(code, request);
    }

    @ResponseWrapper
    @GetMapping("/{code}")
    public ArticleBean details(@PathVariable("code") String code) {
        return articleService.details(code);
    }

    @ResponseWrapper
    @GetMapping
    public PageResponse<ArticleBean> list(@RequestParamAlias QueryArticlesRequest request) {
        return articleService.list(request);
    }
}
