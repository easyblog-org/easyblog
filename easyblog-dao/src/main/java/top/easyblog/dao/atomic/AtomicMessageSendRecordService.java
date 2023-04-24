package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.message.record.QueryMessageSendRecordRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.BusinessMessageRecordMapper;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.dao.auto.model.example.BusinessMessageRecordExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-11 16:53
 */
@Slf4j
@Service
public class AtomicMessageSendRecordService {

    @Autowired
    private BusinessMessageRecordMapper mapper;

    public void create(BusinessMessageRecord record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB]Insert new message send record successfully!Details==>{}", JsonUtils.toJSONString(record));
    }

    public void update(BusinessMessageRecord record) {
        record.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(record);
        log.info("[DB]Update message send record successfully!Details==>{}", JsonUtils.toJSONString(record));
    }

    @RecordNullable
    public BusinessMessageRecord details(QueryMessageSendRecordRequest request) {
        BusinessMessageRecordExample example = new BusinessMessageRecordExample();
        BusinessMessageRecordExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        return Iterables.getFirst(mapper.selectByExample(example), null);
    }

}
