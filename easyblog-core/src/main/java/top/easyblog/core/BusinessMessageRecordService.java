package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.BusinessMessageRecordBean;
import top.easyblog.common.enums.MessageSendStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordsRequest;
import top.easyblog.common.request.message.record.UpdateBusinessMessageRecordRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicBusinessMessageRecordService;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.support.context.BusinessMessageRecordContext;
import top.easyblog.support.event.MessageSendPreparedEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * 创建消息发送记录
     *
     * @param request
     * @return
     */
    public BusinessMessageRecord createMessageRecord(CreateBusinessMessageRecordRequest request) {
        request.setStatus(MessageSendStatus.UNSEND.getCode());
        BusinessMessageRecord record = beanMapper.convertMessageSendRecordCreateReq2MessageSendRecord(request);
        atomicBusinessMessageRecordService.insertOne(record);
        BusinessMessageRecordContext messageRecordContext = beanMapper.convertMessageSendRecord2MessageSendRecordContext(record,request.getIsSync());
        applicationEventPublisher.publishEvent(new MessageSendPreparedEvent(messageRecordContext));
        return record;
    }

    /**
     * 更新消息发送记录
     *
     * @param id
     * @param request
     */
    public void updateMessageRecord(Long id, UpdateBusinessMessageRecordRequest request) {
        BusinessMessageRecord record = atomicBusinessMessageRecordService.queryByRequest(QueryBusinessMessageRecordRequest.builder()
                .id(id).build());
        if (Objects.isNull(record)) {
            throw new BusinessException(EasyResultCode.MESSAGE_RECORD_NOT_FOUND);
        }

        if (Objects.equals(MessageSendStatus.SUCCESS.getCode(), record.getStatus())) {
            throw new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_RECORD_OPERATION);
        }

        BusinessMessageRecord updateRecord = beanMapper.convertMessageSendRecordUpdateReq2MessageSendRecord(record.getId(), request);
        atomicBusinessMessageRecordService.updateByPrimaryKeySelective(updateRecord);
    }

    /**
     * 查询消息发送记录详情
     *
     * @param request
     * @return
     */
    public BusinessMessageRecordBean details(QueryBusinessMessageRecordRequest request) {
        BusinessMessageRecord record = atomicBusinessMessageRecordService.queryByRequest(request);
        return Optional.ofNullable(record)
                .map(item -> beanMapper.convertBusinessMessageRecord2BusinessMessageRecordBean(item)).orElse(null);
    }

    /**
     * 查询消息发送记录列表
     *
     * @param request
     * @return
     */
    public PageResponse<BusinessMessageRecordBean> list(QueryBusinessMessageRecordsRequest request) {
        long amount = atomicBusinessMessageRecordService.countByRequest(request);
        if (Objects.equals(NumberUtils.LONG_ZERO, amount)) {
            return PageResponse.<BusinessMessageRecordBean>builder()
                    .limit(request.getLimit()).offset(request.getOffset()).total(amount).data(Collections.emptyList()).build();
        }

        List<BusinessMessageRecord> records = atomicBusinessMessageRecordService.queryListByRequest(request);
        List<BusinessMessageRecordBean> messageRecordBeans = records.stream().filter(Objects::nonNull)
                .map(record -> beanMapper.convertBusinessMessageRecord2BusinessMessageRecordBean(record))
                .collect(Collectors.toList());

        return PageResponse.<BusinessMessageRecordBean>builder()
                .limit(request.getLimit()).offset(request.getOffset()).total(amount).data(messageRecordBeans).build();
    }


}
