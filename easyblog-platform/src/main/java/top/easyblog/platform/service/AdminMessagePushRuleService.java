package top.easyblog.platform.service;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.MessageConfigRuleBean;
import top.easyblog.common.request.message.rule.CreateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.QueryMessageConfigRulesRequest;
import top.easyblog.common.request.message.rule.UpdateMessageConfigRuleRequest;
import top.easyblog.common.response.PageResponse;

@Slf4j
@Service
public class AdminMessagePushRuleService {

    public void createMessagePushRule(@Valid CreateMessageConfigRuleRequest request) {
    }

    public void update(String code, UpdateMessageConfigRuleRequest request) {
    }

    public MessageConfigRuleBean details(QueryMessageConfigRuleRequest request) {
        return null;
    }

    public PageResponse<MessageConfigRuleBean> list(QueryMessageConfigRulesRequest request) {
        return null;
    }
    
}
