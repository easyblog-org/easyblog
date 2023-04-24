package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.dao.auto.mapper.MessageTemplateMapper;
import top.easyblog.dao.auto.model.MessageTemplate;
import top.easyblog.dao.auto.model.example.MessageTemplateExample;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:43
 */
@Slf4j
@Service
public class AtomicMessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    public MessageTemplate queryByRequest(QueryMessageTemplateRequest request) {
        if (StringUtils.isBlank(request.getTemplateCode())) {
            return null;
        }
        MessageTemplateExample example = new MessageTemplateExample();
        MessageTemplateExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(request.getTemplateCode())){
            criteria.andTemplateCodeEqualTo(request.getTemplateCode());
        }
        return Iterables.getFirst(messageTemplateMapper.selectByExample(example),null);
    }
}
