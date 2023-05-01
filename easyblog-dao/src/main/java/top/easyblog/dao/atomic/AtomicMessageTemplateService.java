package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.MessageTemplateMapper;
import top.easyblog.dao.auto.model.MessageTemplate;
import top.easyblog.dao.auto.model.example.MessageTemplateExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:43
 */
@Slf4j
@Service
public class AtomicMessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;


    public void insertOne(MessageTemplate template) {
        template.setCreateTime(new Date());
        template.setUpdateTime(new Date());
        messageTemplateMapper.insertSelective(template);
        log.info("[DB]Insert new message template.Details==>{}", JsonUtils.toJSONString(template));
    }


    public void updateByPrimaryKeySelective(MessageTemplate template) {
        template.setUpdateTime(new Date());
        messageTemplateMapper.updateByPrimaryKeySelective(template);
        log.info("[DB]Update message template.Details==>{}", JsonUtils.toJSONString(template));
    }

    @RecordNullable
    public MessageTemplate queryByRequest(QueryMessageTemplateRequest request) {
        if (StringUtils.isBlank(request.getTemplateCode())) {
            return null;
        }
        MessageTemplateExample example = new MessageTemplateExample();
        MessageTemplateExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getTemplateCode())) {
            criteria.andTemplateCodeEqualTo(request.getTemplateCode());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            criteria.andNameEqualTo(request.getName());
        }
        return Iterables.getFirst(messageTemplateMapper.selectByExample(example), null);
    }

    public long countByRequest(QueryMessageTemplatesRequest request) {
        MessageTemplateExample example = generateExamples(request);
        return messageTemplateMapper.countByExample(example);
    }

    public List<MessageTemplate> queryListByRequest(QueryMessageTemplatesRequest request) {
        MessageTemplateExample example = generateExamples(request);
        example.setLimit(request.getLimit());
        example.setOffset(request.getOffset());
        return messageTemplateMapper.selectByExample(example);
    }

    private MessageTemplateExample generateExamples(QueryMessageTemplatesRequest request) {
        MessageTemplateExample example = new MessageTemplateExample();
        MessageTemplateExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getTemplateCodes())) {
            criteria.andTemplateCodeIn(request.getTemplateCodes());
        }
        if (CollectionUtils.isNotEmpty(request.getMsgType())) {
            criteria.andMsgTypeIn(request.getMsgType());
        }

        if (CollectionUtils.isNotEmpty(request.getShieldType())) {
            criteria.andShieldTypeIn(request.getShieldType());
        }

        if (CollectionUtils.isNotEmpty(request.getStatus())) {
            criteria.andStatusIn(request.getStatus());
        }

        if (StringUtils.isNotBlank(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }

        return example;
    }

}
