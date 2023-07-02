package top.easyblog.platform.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.bean.H5ArticleBean;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.ArticleService;
import top.easyblog.support.util.ConcurrentUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-07-01 14:57
 */
@Slf4j
@Service
public class H5ArticleService {

    @Autowired
    private ArticleService articleService;


    public PageResponse<H5ArticleBean.ArticleBean> list(QueryArticlesRequest request) {
        PageResponse<ArticleBean> articleBeanPageResponse = articleService.list(request);
        List<ArticleBean> articleBeans = articleBeanPageResponse.getData();
        List<H5ArticleBean.ArticleBean> h5ArticleBeans = articleBeans.stream()
                .map(this::convertToH5ArticleBean).collect(Collectors.toList());
        return PageResponse.<H5ArticleBean.ArticleBean>builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .total(articleBeanPageResponse.getTotal())
                .data(h5ArticleBeans).build();
    }

    public ArticleBean details(String code, String sections) {
        return articleService.details(code, sections);
    }

    public H5ArticleBean queryList() {
        H5ArticleBean articleBean = new H5ArticleBean();
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(() -> {
            PageResponse<H5ArticleBean.ArticleBean> articleBeanPageResponse = list(QueryArticlesRequest.builder()
                    .isTop(true)
                    .orderCause("create_time")
                    .orderDir("desc")
                    .offset(0)
                    .limit(7)
                    .build());
            Assert.notNull(articleBeanPageResponse, "Query article list return null response.");
            List<H5ArticleBean.ArticleBean> topHotArticles = articleBeanPageResponse.getData();
            List<List<H5ArticleBean.ArticleBean>> topHotArticlesPartition = Lists.partition(topHotArticles, 5);
            articleBean.setSwiperArticleList(Iterables.getFirst(topHotArticlesPartition, Collections.emptyList()));
            articleBean.setSwiperArticleSideList(topHotArticles.size() <= 5 ? Collections.emptyList() :
                    Iterables.getLast(topHotArticlesPartition, Collections.emptyList()));
        });

        tasks.add(() -> {
            PageResponse<H5ArticleBean.ArticleBean> articleBeanPageResponse = list(QueryArticlesRequest.builder()
                    .orderCause("create_time")
                    .orderDir("desc")
                    .offset(0)
                    .limit(20)
                    .build());
            Assert.notNull(articleBeanPageResponse, "Query article list return null response.");
            articleBean.setNewestArticleList(articleBeanPageResponse.getData());
        });


        ConcurrentUtils.executeTaskInBlockModel(tasks, null);
        return articleBean;
    }

    private H5ArticleBean.ArticleBean convertToH5ArticleBean(ArticleBean articleBean) {
        H5ArticleBean.ArticleBean h5ArticleBean = new H5ArticleBean.ArticleBean();
        h5ArticleBean.setId(articleBean.getId());
        h5ArticleBean.setCode(articleBean.getCode());
        h5ArticleBean.setTitle(articleBean.getTitle());
        h5ArticleBean.setFeaturedImage(articleBean.getFeaturedImage());
        h5ArticleBean.setStatus(articleBean.getStatus());
        h5ArticleBean.setIsTop(articleBean.getIsTop());
        h5ArticleBean.setContent(articleBean.getContent());
        h5ArticleBean.setCreateTime(articleBean.getCreateTime());
        h5ArticleBean.setUpdateTime(articleBean.getUpdateTime());

        if (Objects.nonNull(articleBean.getCategories())) {
            List<String> categoriesName = articleBean.getCategories().stream()
                    .map(ArticleCategoryBean::getName).collect(Collectors.toList());
            h5ArticleBean.setCategories(categoriesName);
        }

        H5ArticleBean.ArticleAuthor articleAuthor = new H5ArticleBean.ArticleAuthor();
        if (Objects.nonNull(articleBean.getAuthor())) {
            articleAuthor.setId(articleBean.getAuthor().getId());
            articleAuthor.setCode(articleBean.getAuthor().getCode());
            articleAuthor.setNickName(articleBean.getAuthor().getNickName());
        }
        if (Objects.nonNull(articleBean.getAuthorAvatar())) {
            articleAuthor.setHeaderImgUrl(articleBean.getAuthorAvatar().getHeaderImgUrl());

        }
        h5ArticleBean.setAuthor(articleAuthor);
        return h5ArticleBean;
    }
}
