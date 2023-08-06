package top.easyblog.service.strategy;

import top.easyblog.service.strategy.context.CaptchaPushContext;

/**
 * @author: frank.huang
 * @date: 2023-08-06 16:44
 */
public interface ICaptchaStrategy {

    /**
     * 验证码策略类型
     * @return
     */
    String getCaptchaType();

    /**
     * 发送验证码
     * @param context
     */
    void sendCaptcha(CaptchaPushContext context);

}
