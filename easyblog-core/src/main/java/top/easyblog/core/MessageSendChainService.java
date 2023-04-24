package top.easyblog.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import top.easyblog.core.processor.chian.MessageProcessChain;
import top.easyblog.support.context.MessageProcessorContext;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-02-12 12:43
 */
@Service
public class MessageSendChainService implements ApplicationContextAware {

    private List<MessageProcessChain> messageProcessChainList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取指定的接口实现类，并按照sort进行排序，放入List中
        Map<String, MessageProcessChain> serviceMap = applicationContext.getBeansOfType(MessageProcessChain.class);
        messageProcessChainList = serviceMap.values().stream()
                .sorted(Comparator.comparing(MessageProcessChain::priority))
                .collect(Collectors.toList());
    }


    /**
     * 执行处理
     * @param context
     * @return
     */
    public MessageProcessorContext execute(MessageProcessorContext context){
        for (MessageProcessChain processor : messageProcessChainList) {
            context =processor.process(context);
        }
        return context;
    }
}
