package top.easyblog.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.ArticleCategoryBean;
import top.easyblog.common.bean.H5ArticleBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.article.ArticleStatisticsRequest;
import top.easyblog.common.request.article.QueryArticleEventRequest;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.ArticleEventService;
import top.easyblog.core.ArticleService;
import top.easyblog.platform.service.strategy.ArticleStatisticUpdateStrategy;
import top.easyblog.platform.service.strategy.ArticleStatisticUpdateStrategyContext;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-07-01 14:57
 */
@Slf4j
@Service
public class UserArticleService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleEventService articleEventService;


    public PageResponse<H5ArticleBean.ArticleBean> list(QueryArticlesRequest request) {
        PageResponse<ArticleBean> articleBeanPageResponse = articleService.list(request);
        List<ArticleBean> articleBeans = articleBeanPageResponse.getData();
        List<H5ArticleBean.ArticleBean> h5ArticleBeans = articleBeans.stream()
                .map(this::convertToH5ArticleBean).filter(Objects::nonNull).collect(Collectors.toList());
        return PageResponse.<H5ArticleBean.ArticleBean>builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .total(articleBeanPageResponse.getTotal())
                .data(h5ArticleBeans).build();
    }

    public H5ArticleBean.ArticleBean details(String code, String sections) {
        ArticleBean articleBean = articleService.details(code, sections);
        return convertToH5ArticleBean(articleBean);
    }


    private H5ArticleBean.ArticleBean convertToH5ArticleBean(ArticleBean articleBean) {
        if (Objects.isNull(articleBean)) return null;
        H5ArticleBean.ArticleBean h5ArticleBean = new H5ArticleBean.ArticleBean();
        h5ArticleBean.setId(articleBean.getId());
        h5ArticleBean.setCode(articleBean.getCode());
        h5ArticleBean.setTitle(articleBean.getTitle());
        h5ArticleBean.setFeaturedImage(articleBean.getFeaturedImage());
        h5ArticleBean.setStatus(articleBean.getStatus());
        h5ArticleBean.setIsTop(articleBean.getIsTop());
        h5ArticleBean.setContent(articleBean.getContent());
        h5ArticleBean.setFavoritesNum(articleBean.getFavoritesNum());
        h5ArticleBean.setLikesNum(articleBean.getLikesNum());
        h5ArticleBean.setPageViews(articleBean.getPageViews());
        h5ArticleBean.setReportsNum(articleBean.getReportsNum());
        h5ArticleBean.setRetweetsNum(articleBean.getRetweetsNum());
        h5ArticleBean.setCreateTime(Optional.ofNullable(articleBean.getCreateTime()).map(Date::getTime).orElse(0L));
        h5ArticleBean.setUpdateTime(Optional.ofNullable(articleBean.getUpdateTime()).map(Date::getTime).orElse(0L));

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

    public void update(String code, UpdateArticleRequest request) {
        articleService.updateArticle(code, request);
    }

    /**
     * 更新文章指标（事件）
     *
     * @param request
     */
    public void updateStatistic(ArticleStatisticsRequest request) {
        ArticleStatisticUpdateStrategy statisticStrategy = ArticleStatisticUpdateStrategyContext.getStatisticStrategy(request.getStatisticIndexName());
        if (Objects.isNull(statisticStrategy)) {
            throw new BusinessException(EasyResultCode.STATISTIC_INDEX_NOT_FOUND);
        }

        statisticStrategy.updateStatistic(request);
    }

    /**
     * 查询文章指标（事件）
     *
     * @param request
     * @return
     */
    public Long statistic(QueryArticleEventRequest request) {
        return articleEventService.countByRequest(request);
    }

    /**
     * 查询文档总数
     *
     * @param request
     * @return
     */
    public Long countArticleByRequest(QueryArticlesRequest request) {
        return articleService.countByRequest(request);
    }

}
