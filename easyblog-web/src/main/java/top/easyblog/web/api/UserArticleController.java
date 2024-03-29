package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.H5ArticleBean;
import top.easyblog.common.request.article.ArticleStatisticsRequest;
import top.easyblog.common.request.article.QueryArticleEventRequest;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.UserArticleService;
import top.easyblog.web.annotation.RequestParamAlias;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-07-01 14:50
 */
@RequestMapping("/h5/v1/article")
@RestController
public class UserArticleController {

    @Autowired
    private UserArticleService articleService;


    /**
     * 文章列表
     *
     * @param request
     * @return
     */
    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<H5ArticleBean.ArticleBean> list(@RequestParamAlias QueryArticlesRequest request) {
        return articleService.list(request);
    }

    /**
     * 文章详情
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/{code}")
    public H5ArticleBean.ArticleBean details(@PathVariable("code") String code,
                                             @RequestParam(value = "sections", required = false) String sections) {
        return articleService.details(code, sections);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void updateArticle(@PathVariable("code") String code, @RequestBody UpdateArticleRequest request) {
        articleService.update(code, request);
    }

    @ResponseWrapper
    @PutMapping("/statistics")
    public void updateStatistic(@RequestBody ArticleStatisticsRequest request) {
        articleService.updateStatistic(request);
    }

    @ResponseWrapper
    @GetMapping("/statistics")
    public Long statistic(@RequestParamAlias QueryArticleEventRequest request) {
        return articleService.statistic(request);
    }

    @ResponseWrapper
    @GetMapping("/count")
    public Long countArticles(@RequestParamAlias QueryArticlesRequest request) {
        return articleService.countArticleByRequest(request);
    }

}
