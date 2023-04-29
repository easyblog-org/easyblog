package top.easyblog.core.processor.chian.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.processor.chian.MessageProcessChain;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.core.strategy.MessagePushStrategyContext;
import top.easyblog.dao.atomic.AtomicMessageSendRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.MessageProcessorContext;
import top.easyblog.support.context.MessageSendContext;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-11 18:28
 */
@Slf4j
@Component
public class MessageSendProcessChain implements MessageProcessChain {

    @Autowired
    private AtomicMessageSendRecordService atomicMessageSendRecordService;

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public MessageProcessorContext process(MessageProcessorContext context) {
        BusinessMessageRecord messageSendRecord = atomicMessageSendRecordService.details(QueryBusinessMessageRecordRequest.builder()
                .id(context.getMessageRecordId())
                .build());
        if (Objects.nonNull(messageSendRecord)) {
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR,
                    String.format("Not found message send record by id:%s", context.getMessageRecordId()));
        }
        MessagePushStrategy pushStrategy = MessagePushStrategyContext.getMessageSendStrategy(context.getChannel());
        if (Objects.isNull(pushStrategy)) {
            throw new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_SEND_CHANNEL);
        }
        MessageSendContext sendContext = MessageSendContext.builder()
                .sendRecordId(context.getMessageRecordId())
                .attachments(Lists.newArrayList())
                .build();
        pushStrategy.push(sendContext);
        return context;
    }

}
