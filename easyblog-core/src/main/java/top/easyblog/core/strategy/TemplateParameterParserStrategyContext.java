package top.easyblog.core.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.core.strategy.template.TemplateParameterParseStrategy;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-02-11 21:58
 */
@Slf4j
@Component
public class TemplateParameterParserStrategyContext {

    @Autowired
    private List<TemplateParameterParseStrategy> templateParameterParseStrategies;

    private static Map<Byte, TemplateParameterParseStrategy> templateParameterParseStrategyMap = new HashMap<>();

    @PostConstruct
    private void initTemplateParameterParseStrategyMap() {
        log.info("Start init template parameter parse strategy....");
        templateParameterParseStrategyMap = templateParameterParseStrategies.stream()
                .collect(Collectors.toMap(TemplateParameterParseStrategy::getParameterType, Function.identity(), (x, y) -> x));
        log.info("Init template parameter parse strategy successfully!");
    }

    public static TemplateParameterParseStrategy getMessageSendStrategy(Byte sendType) {
        return templateParameterParseStrategyMap.get(sendType);
    }

}
