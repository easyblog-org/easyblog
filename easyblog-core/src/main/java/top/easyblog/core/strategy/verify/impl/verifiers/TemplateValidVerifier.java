package top.easyblog.core.strategy.verify.impl.verifiers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.core.strategy.verify.VerifyStrategy;
import top.easyblog.dao.atomic.AtomicMessageTemplateService;
import top.easyblog.dao.auto.model.MessageTemplate;
import top.easyblog.core.strategy.verify.VerifyContext;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-12 14:29
 */
@Slf4j
@Component
public class TemplateValidVerifier implements VerifyStrategy {


    @Autowired
    private AtomicMessageTemplateService atomicMessageTemplateService;

    @Override
    public boolean verify(VerifyContext request) {
        if (StringUtils.isBlank(request.getTemplateCode())) {
            return false;
        }
        MessageTemplate messageTemplate = atomicMessageTemplateService.queryByRequest(QueryMessageTemplateRequest.builder()
                .templateCode(request.getTemplateCode())
                .build());
        return Objects.nonNull(messageTemplate) &&
                Objects.equals(Boolean.FALSE, messageTemplate.getDeleted()) &&
                StringUtils.isNotBlank(messageTemplate.getMsgContent()) &&
                Objects.isNull(messageTemplate.getSendChannel());
    }
}
