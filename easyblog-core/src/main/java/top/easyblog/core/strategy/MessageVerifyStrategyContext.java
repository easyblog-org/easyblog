package top.easyblog.core.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.core.strategy.verify.MessageVerifyStrategy;

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
public class MessageVerifyStrategyContext {

    @Autowired
    private List<MessageVerifyStrategy> messageVerifyStrategies;

    private static Map<Byte, MessageVerifyStrategy> messageVerifyStrategyHashMap = new HashMap<>();

    @PostConstruct
    private void initMessageSendStrategyMap() {
        log.info("Start init message send strategy....");
        messageVerifyStrategyHashMap = messageVerifyStrategies.stream()
                .collect(Collectors.toMap(MessageVerifyStrategy::getPushType, Function.identity(), (x, y) -> x));
        log.info("Init message send strategy successfully!");
    }

    public static MessageVerifyStrategy getMessageVerifyStrategy(Byte sendType) {
        return messageVerifyStrategyHashMap.get(sendType);
    }

}
