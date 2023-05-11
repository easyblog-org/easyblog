package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.enums.MessageSendStatus;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.QueryBusinessMessageRecordsRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.BusinessMessageRecordMapper;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.dao.auto.model.example.BusinessMessageRecordExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:45
 */
@Slf4j
@Service
public class AtomicBusinessMessageRecordService {

    @Autowired
    private BusinessMessageRecordMapper mapper;

    public void insertOne(BusinessMessageRecord record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB] Insert new bussiness message record.Details==>{}", JsonUtils.toJSONString(record));
    }

    @RecordNullable
    public BusinessMessageRecord queryByRequest(QueryBusinessMessageRecordRequest request) {
        BusinessMessageRecordExample example = new BusinessMessageRecordExample();
        BusinessMessageRecordExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (StringUtils.isNotBlank(request.getBusinessEvent()) &&
                StringUtils.isNotBlank(request.getBusinessModule())) {
            criteria.andBusinessEventEqualTo(request.getBusinessEvent());
            criteria.andBusinessModuleEqualTo(request.getBusinessModule());
        }
        return Iterables.getFirst(mapper.selectByExample(example), null);
    }

    public void updateByPrimaryKeySelective(BusinessMessageRecord record) {
        record.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(record);
        log.info("[DB] Update business message record.Details==>{}", JsonUtils.toJSONString(record));
    }

    public long countByRequest(QueryBusinessMessageRecordsRequest request) {
        return mapper.countByExample(generateExamples(request));
    }

    public List<BusinessMessageRecord> queryListByRequest(QueryBusinessMessageRecordsRequest request) {
        BusinessMessageRecordExample example = generateExamples(request);
        example.setLimit(request.getLimit());
        example.setOffset(request.getOffset());
        return mapper.selectByExample(example);
    }

    private BusinessMessageRecordExample generateExamples(QueryBusinessMessageRecordsRequest request) {
        BusinessMessageRecordExample example = new BusinessMessageRecordExample();
        BusinessMessageRecordExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }
        if (StringUtils.isNotBlank(request.getBusinessModule())) {
            criteria.andBusinessModuleEqualTo(request.getBusinessModule());
        }
        if (StringUtils.isNotBlank(request.getBusinessEvent())) {
            criteria.andBusinessEventEqualTo(request.getBusinessEvent());
        }
        if (CollectionUtils.isNotEmpty(request.getStatus())) {
            criteria.andStatusIn(request.getStatus());
        }
        criteria.andDeletedEqualTo(Boolean.FALSE);
        if (Objects.nonNull(request.getDeleted()) &&
                Objects.equals(Boolean.TRUE, request.getDeleted())) {
            criteria.andDeletedEqualTo(Boolean.TRUE);
        }
        return example;
    }
}
