package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.request.message.record.CreateMessageSendRecordRequest;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicBusinessMessageRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.BusinessMessageRecordContext;
import top.easyblog.support.context.MessageProcessorContext;
import top.easyblog.support.event.MessageSendPreparedEvent;

/**
 * 创建消息发送
 *
 * @author: frank.huang
 * @date: 2023-02-12 12:33
 */
@Slf4j
@Service
public class BusinessMessageRecordService {


    @Autowired
    private AtomicBusinessMessageRecordService atomicBusinessMessageRecordService;

    @Autowired
    private MessageSendChainService messageSendChainService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BeanMapper beanMapper;


    public void createMessageRecord(CreateMessageSendRecordRequest request) {
        BusinessMessageRecord record = beanMapper.convertMessageSendRecordCreateReq2MessageSendRecord(request);
        BusinessMessageRecordContext messageRecordContext = beanMapper.convertMessageSendRecord2MessageSendRecordContext(record);
        applicationEventPublisher.publishEvent(new MessageSendPreparedEvent(messageRecordContext));
    }


    public void updateMessageRecord() {

    }


    public void details() {

    }


    public void list() {

    }


}
