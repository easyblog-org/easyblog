package top.easyblog.platform.service.strategy;

import top.easyblog.common.request.article.ArticleStatisticsRequest;

/**
 * @author: frank.huang
 * @date: 2023-07-16 16:18
 */
public interface ArticleStatisticUpdateStrategy {

    /**
     * 获取统计指标名称
     *
     * @return
     */
    String getStatisticIndexName();

    /**
     * 更新统计指标
     *
     * @param request
     */
    void updateStatistic(ArticleStatisticsRequest request);


}
