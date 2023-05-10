package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRulesRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.MessageConfigRuleMapper;
import top.easyblog.dao.auto.model.MessageConfigRule;
import top.easyblog.dao.auto.model.example.MessageConfigRuleExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:44
 */
@Slf4j
@Service
public class AtomicMessageConfigRuleService {


    @Autowired
    private MessageConfigRuleMapper messageConfigRuleMapper;

    public void insertOne(MessageConfigRule record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        messageConfigRuleMapper.insertSelective(record);
        log.info("[DB]Insert new message config rule successfully!Details==>{}", JsonUtils.toJSONString(record));
    }

    @RecordNullable
    public MessageConfigRule queryByRequest(QueryMessageConfigRuleRequest request) {
        if (queryParamAllEmpty(request)) {
            return null;
        }
        MessageConfigRuleExample example = new MessageConfigRuleExample();
        MessageConfigRuleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andTemplateCodeEqualTo(request.getCode());
        }
        return Iterables.getFirst(messageConfigRuleMapper.selectByExample(example), null);
    }

    private boolean queryParamAllEmpty(QueryMessageConfigRuleRequest request) {
        return StringUtils.isBlank(request.getCode());
    }

    public void updateByPKSelective(MessageConfigRule record) {
        record.setUpdateTime(new Date());
        messageConfigRuleMapper.updateByPrimaryKeySelective(record);
        log.info("[DB]Update new message config rule successfully.Details==>{}", JsonUtils.toJSONString(record));
    }

    public long countByRequest(QueryMessageConfigRulesRequest request) {
        MessageConfigRuleExample example = generateExamples(request);
        return messageConfigRuleMapper.countByExample(example);
    }

    public List<MessageConfigRule> queryListByRequest(QueryMessageConfigRulesRequest request) {
        MessageConfigRuleExample example = generateExamples(request);
        example.setLimit(request.getLimit());
        example.setOffset(request.getOffset());
        return messageConfigRuleMapper.selectByExample(example);
    }

    private MessageConfigRuleExample generateExamples(QueryMessageConfigRulesRequest request) {
        MessageConfigRuleExample example = new MessageConfigRuleExample();
        MessageConfigRuleExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }
        if (StringUtils.isNotBlank(request.getTemplateCode())) {
            criteria.andTemplateCodeEqualTo(request.getTemplateCode());
        }
        if (Objects.nonNull(request.getChannel())) {
            criteria.andChannelEqualTo(request.getChannel());
        }
        if (CollectionUtils.isNotEmpty(request.getBusinessEvents())) {
            criteria.andBusinessEventIn(request.getBusinessEvents());
        }
        if (CollectionUtils.isNotEmpty(request.getBusinessModules())) {
            criteria.andBusinessModuleIn(request.getBusinessModules());
        }
        criteria.andDeletedEqualTo(Boolean.FALSE);
        if (Objects.nonNull(request.getDeleted())) {
            criteria.andDeletedEqualTo(request.getDeleted());
        }
        return example;
    }
}
