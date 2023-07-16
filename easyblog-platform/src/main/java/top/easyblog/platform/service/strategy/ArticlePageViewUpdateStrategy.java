package top.easyblog.platform.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.request.article.ArticleStatisticsRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;
import top.easyblog.core.ArticleService;

/**
 * @author: frank.huang
 * @date: 2023-07-16 16:21
 */
@Component
public class ArticlePageViewUpdateStrategy implements ArticleStatisticUpdateStrategy{

    @Autowired
    private ArticleService articleService;


    @Override
    public String getStatisticIndexName() {
        return "page_views";
    }

    @Override
    public void updateStatistic(ArticleStatisticsRequest request) {
        articleService.updateArticle(request.getCode(), UpdateArticleRequest.builder()
                .pageViews(request.getIncrement()).build());
    }
}
