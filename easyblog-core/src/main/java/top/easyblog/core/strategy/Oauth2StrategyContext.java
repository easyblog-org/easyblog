package top.easyblog.core.strategy;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.service.strategy.IOauthStrategy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:37
 */
@Slf4j
@Component
public class Oauth2StrategyContext {

    private static Map<Integer, IOauthStrategy> oauthStrategyMap = Maps.newHashMap();

    @Autowired
    private List<IOauthStrategy> oauthStrategies;

    public static IOauthStrategy getOauthStrategy(Integer identifierType) {
        IOauthStrategy oauthStrategy = oauthStrategyMap.get(identifierType);
        IdentifierType type = IdentifierType.subCodeOf(identifierType);
        if (Objects.isNull(type) || Objects.equals(IdentifierType.UNKNOWN, type)) {
            throw new UnsupportedOperationException(EasyResultCode.INCORRECT_OAUTH_POLICY.getCode());
        }
        return oauthStrategy;
    }

    @PostConstruct
    public void initOauthStrategyMap() {
        log.info("Start init oauth strategy.....");
        oauthStrategyMap = oauthStrategies.stream().collect(Collectors.toMap(IOauthStrategy::getIdentifierType, Function.identity(), (x, y) -> x));
        log.info("Init oauth strategy successfully!");
    }
}
