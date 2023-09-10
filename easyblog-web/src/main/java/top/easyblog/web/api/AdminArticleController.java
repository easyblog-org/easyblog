package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.request.article.*;
import top.easyblog.common.response.PageResponse;
import top.easyblog.dao.auto.model.Article;
import top.easyblog.platform.service.AdminAccountService;
import top.easyblog.platform.service.AdminArticleService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2023-02-25 16:28
 */
@RequestMapping("/admin/v1/article")
@RestController
public class AdminArticleController {

    @Autowired
    private AdminArticleService articleService;

    @ResponseWrapper
    @PostMapping
    public Object create(@RequestBody @Valid CreateArticleRequest request) {
        return articleService.createArticle(request);
    }

    @ResponseWrapper
    @GetMapping
    public ArticleBean query(@RequestParam("code") String code,
                             @RequestParam(value = "sections", required = false) String sections) {
        return articleService.details(code, sections);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void update(@PathVariable("code") String code,
                       @RequestBody @Valid UpdateArticleRequest request) {
        articleService.updateArticle(code, request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<ArticleBean> queryList(@Valid @RequestParamAlias QueryArticlesRequest request) {
        return articleService.list(request);
    }

    @ResponseWrapper
    @GetMapping("/category/list")
    public PageResponse<ArticleCategoryBean> queryArticleCategoryList(@Valid @RequestParamAlias QueryArticleCategoryListRequest request) {
        return articleService.queryArticleCategoryList(request);
    }


    @ResponseWrapper
    @PostMapping("/category")
    public void createCategory(@RequestBody @Valid CreateArticleCategoryRequest request) {
        articleService.createCategory(request);
    }

    @ResponseWrapper
    @PutMapping("/category/{id}")
    public void updateCategory(@PathVariable("id") Long id,
                               @RequestBody @Valid UpdateArticleCategoryRequest request) {
        articleService.updateArticleCategory(id, request);
    }

    @ResponseWrapper
    @PostMapping("/category/{id}")
    public void deleteCategory(@PathVariable("id") Long id, @RequestParam("pwd") String password) {
        articleService.deleteCategory(id,password);
    }

}
