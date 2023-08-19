package top.easyblog.core.strategy.push.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.ValidationUtils;
import top.easyblog.client.clients.OnBukaClient;
import top.easyblog.client.dto.request.BukaSmsSendRequest;
import top.easyblog.client.dto.response.dto.BukaSmsSendDTO;
import top.easyblog.common.enums.MessageSendChannel;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.strategy.push.MessagePushStrategy;
import top.easyblog.support.context.MessageSendContext;
import top.easyblog.support.util.JsonUtils;

import javax.mail.MessagingException;

/**
 * 短信推送策略
 *
 * @author: frank.huang
 * @date: 2023-02-11 20:10
 */
@Slf4j
@Component
public class SMSPushStrategy implements MessagePushStrategy {

    @Autowired
    private OnBukaClient onBukaClient;

    @Value("${sms.onbuka.app-id}")
    private String appId;

    //发送成功
    private static final String SUCCESS = "0";


    @Override
    public byte getPushType() {
        return MessageSendChannel.SMS.getCode();
    }

    @Override
    public void push(MessageSendContext context) throws MessagingException {
        BukaSmsSendDTO bukaSmsSendDTO = onBukaClient.sendSms(BukaSmsSendRequest.builder()
                .appId(appId)
                .content(context.getContent())
                .numbers(context.getReceiver())
                .build());
        Assert.notNull(bukaSmsSendDTO, "Send sms failed: unknown reason");
        if (!StringUtils.equals(SUCCESS, bukaSmsSendDTO.getStatus())) {
            throw new BusinessException(EasyResultCode.SMS_PUSH_FAILED, bukaSmsSendDTO.getReason());
        }
        log.info("Send sms to {} success!", context.getReceiver());
    }
}
