package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:44
 */
@Getter
@AllArgsConstructor
public enum MessageSendChannel {
    /**
     * 纯文本邮件
     */
    PLAIN_EMAIL((byte) 10, "纯文本邮件"),
    /**
     * 带附件的邮件 attachment
     */
    ATTACHMENT_EMAIL((byte) 11, "HTML邮件"),
    /**
     * 短信
     */
    SMS((byte) 20, "短信"),
    /**
     * 微信通知
     */
    WX((byte) 30, "微信通知"),

    ;

    private final byte code;
    private final String desc;


    public static MessageSendChannel codeOf(Byte code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.stream(MessageSendChannel.values())
                .filter(channel -> Objects.equals(channel.getCode(), code))
                .findAny().orElse(null);
    }
}
