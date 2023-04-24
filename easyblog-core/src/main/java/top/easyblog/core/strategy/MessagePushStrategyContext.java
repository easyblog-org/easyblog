package top.easyblog.core.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.core.strategy.push.MessagePushStrategy;

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
public class MessagePushStrategyContext {

    @Autowired
    private List<MessagePushStrategy> messageSendStrategies;

    private static Map<Byte, MessagePushStrategy> messageSendStrategyHashMap = new HashMap<>();

    @PostConstruct
    private void initMessageSendStrategyMap() {
        log.info("Start init message send strategy....");
        messageSendStrategyHashMap = messageSendStrategies.stream()
                .collect(Collectors.toMap(MessagePushStrategy::getPushType, Function.identity(), (x, y) -> x));
        log.info("Init message send strategy successfully!");
    }

    public static MessagePushStrategy getMessageSendStrategy(Byte sendType) {
        return messageSendStrategyHashMap.get(sendType);
    }

}
