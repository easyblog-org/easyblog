package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.H5ArticleBean;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.platform.service.H5ArticleService;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-07-01 14:50
 */
@RequestMapping("/h5/v1/article")
@RestController
public class H5ArticleController {

    @Autowired
    private H5ArticleService articleService;


    /**
     * 文章列表
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/index")
    public H5ArticleBean queryList(){
        return articleService.queryList();
    }

    /**
     * 文章列表
     *
     * @param request
     * @return
     */
    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<H5ArticleBean.ArticleBean> list(QueryArticlesRequest request) {
        return articleService.list(request);
    }

    /**
     * 文章详情
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/{code}")
    public ArticleBean details(@PathVariable("code") String code,
                               @RequestParam("sections") String sections) {
        return articleService.details(code, sections);
    }

}
