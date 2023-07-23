package top.easyblog.platform.service.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.request.article.ArticleStatisticsRequest;
import top.easyblog.common.request.article.CreateArticleEventRequest;
import top.easyblog.common.request.article.QueryArticleEventRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;
import top.easyblog.core.ArticleEventService;
import top.easyblog.core.ArticleService;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.dao.auto.model.ArticleEvent;
import top.easyblog.platform.service.strategy.ArticleStatisticUpdateStrategy;
import top.easyblog.support.util.JsonUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-07-23 17:29
 */
@Component
public abstract class AbstractArticleStatisticStrategy implements ArticleStatisticUpdateStrategy {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleEventService articleEventService;

    @Transaction
    @Override
    public void updateStatistic(ArticleStatisticsRequest request) {
        if (skipUpdate(request)) {
            return;
        }

        ArticleBean details = articleService.details(request.getCode(), null);
        Assert.notNull(details, "Statistics object not found:" + JsonUtils.toJSONString(request));

        articleEventService.saveArticleEvent(CreateArticleEventRequest.builder()
                .articleCode(request.getCode())
                .userCode(details.getAuthorId())
                .operator(request.getOperator())
                .event(this.getStatisticIndexName()).build());

        articleService.updateArticle(request.getCode(), convertStatisticUpdateRequest(request));
    }

    protected abstract UpdateArticleRequest convertStatisticUpdateRequest(ArticleStatisticsRequest request);


    protected boolean skipUpdate(ArticleStatisticsRequest request) {
        if (StringUtils.isBlank(request.getOperator())) return false;

        List<ArticleEvent> list = articleEventService.list(QueryArticleEventRequest.builder()
                .operators(Collections.singletonList(request.getOperator()))
                .events(Collections.singletonList(getStatisticIndexName())).build());
        return CollectionUtils.isNotEmpty(list);
    }
}
