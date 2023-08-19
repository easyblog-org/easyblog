package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: frank.huang
 * @date: 2023-08-06 16:58
 */
@Getter
@AllArgsConstructor
public enum NoticeMessageEvent {

    REGISTER_CAPTCHA_EMAIL_NOTICE("register-captcha-email-notice"),
    PHONE_LOGIN_CAPTCHA_NOTICE("phone-login-captcha-notice"),

    ;

    private final String format;
}
