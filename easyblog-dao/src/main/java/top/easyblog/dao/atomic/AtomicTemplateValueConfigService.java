package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.message.config.QueryTemplateValueConfigRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.TemplateValueConfigMapper;
import top.easyblog.dao.auto.model.TemplateValueConfig;
import top.easyblog.dao.auto.model.example.TemplateValueConfigExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:42
 */
@Slf4j
@Service
public class AtomicTemplateValueConfigService {

    @Autowired
    private TemplateValueConfigMapper templateValueConfigMapper;

    public void insertOne(TemplateValueConfig record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        templateValueConfigMapper.insertSelective(record);
        log.info("[DB]Create template value config successfully!Details==>{}", JsonUtils.toJSONString(record));
    }

    @RecordNullable
    public TemplateValueConfig queryByRequest(QueryTemplateValueConfigRequest request) {
        TemplateValueConfigExample example = new TemplateValueConfigExample();
        TemplateValueConfigExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        return Iterables.getFirst(templateValueConfigMapper.selectByExample(example), null);
    }

    public void updateByPKSelective(TemplateValueConfig record) {
        record.setUpdateTime(new Date());
        templateValueConfigMapper.updateByPrimaryKey(record);
        log.info("[DB]Update template value config successfully!Details==>{}", JsonUtils.toJSONString(record));
    }
}
