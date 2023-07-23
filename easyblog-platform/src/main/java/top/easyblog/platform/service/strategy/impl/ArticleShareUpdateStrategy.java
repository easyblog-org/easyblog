package top.easyblog.platform.service.strategy.impl;

import org.springframework.stereotype.Component;
import top.easyblog.common.enums.ArticleEventEnum;
import top.easyblog.common.request.article.ArticleStatisticsRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;

/**
 * @author: frank.huang
 * @date: 2023-07-16 16:21
 */
@Component
public class ArticleShareUpdateStrategy extends AbstractArticleStatisticStrategy {


    @Override
    public String getStatisticIndexName() {
        return ArticleEventEnum.SHARE.name().toLowerCase();
    }


    @Override
    public UpdateArticleRequest convertStatisticUpdateRequest(ArticleStatisticsRequest request) {
        return UpdateArticleRequest.builder().build();
    }
}
