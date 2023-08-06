package top.easyblog.core.strategy.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.CaptchaSendChannel;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.enums.NoticeMessageEvent;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.core.BusinessMessageRecordService;
import top.easyblog.core.strategy.MessagePushStrategyContext;
import top.easyblog.core.strategy.push.MessagePushStrategy;
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
        return String.format("%s-%s", "REGISTER", CaptchaSendChannel.EMAIL.getCode());
    }

    @Override
    public void sendCaptcha(CaptchaPushContext context) {
        log.info("Start send register email record to user email:{}", context.getIdentifier());
        Map<String, String> businessMessage = new HashMap<String, String>() {{
            put("email", context.getIdentifier());
            put("captcha_code", context.getCaptchaCode());
        }};
        messageRecordService.createMessageRecord(CreateBusinessMessageRecordRequest.builder()
                .businessModule("easyblog")
                .businessEvent(NoticeMessageEvent.REGISTER_CAPTCHA_EMAIL_NOTICE.getFormat())
                .businessMessage(JsonUtils.toJSONString(businessMessage))
                .build());
    }
}
