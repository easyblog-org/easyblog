package top.easyblog.platform.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-07-16 16:28
 */
@Component
public class ArticleStatisticUpdateStrategyContext {

    @Autowired
    private List<ArticleStatisticUpdateStrategy> articleStatisticUpdateStrategyList;

    private static Map<String, ArticleStatisticUpdateStrategy> strategyMap;


    @PostConstruct
    public void initStrategyMap() {
        strategyMap = articleStatisticUpdateStrategyList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ArticleStatisticUpdateStrategy::getStatisticIndexName,
                        Function.identity(), (e1, e2) -> e1));
    }


    public static ArticleStatisticUpdateStrategy getStatisticStrategy(String statisticIndexName) {
        return strategyMap.get(statisticIndexName);
    }

}
