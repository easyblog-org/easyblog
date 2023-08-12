package top.easyblog.core.strategy.captcha;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.bean.notice.EmailRegisterCaptchaBean;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.enums.NoticeMessageEvent;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.service.strategy.ICaptchaStrategy;
import top.easyblog.service.strategy.context.CaptchaPushContext;
import top.easyblog.support.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2023-08-06 16:51
 */
@Slf4j
@Component
public class EmailRegisterCaptchaStrategy implements ICaptchaStrategy {

    @Autowired
    private BusinessMessageRecordService messageRecordService;

    @Override
    public String getCaptchaType() {
        return String.format("%s-%s", "REGISTER", IdentifierType.E_MAIL.getCode());
    }

    @Override
    public void sendCaptcha(CaptchaPushContext context) {
        log.info("Start send register email record to user email:{}", context.getIdentifier());

        if (StringUtils.isBlank(context.getIdentifier())) {
            throw new BusinessException(EasyResultCode.INTERNAL_ERROR);
        }
        String identifier = context.getIdentifier();
        String username = identifier.substring(0, identifier.lastIndexOf("@"));
        EmailRegisterCaptchaBean registerCaptchaBean = EmailRegisterCaptchaBean.builder()
                .username(username)
                .email(identifier)
                .captchaCode(context.getCaptchaCode())
                .build();
        messageRecordService.createMessageRecord(CreateBusinessMessageRecordRequest.builder()
                .businessModule("easyblog")
                .businessEvent(NoticeMessageEvent.REGISTER_CAPTCHA_EMAIL_NOTICE.getFormat())
                .businessMessage(JsonUtils.toJSONString(registerCaptchaBean))
                .build());
    }
}
