package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 消息模板类型
 *
 * @author: frank.huang
 * @date: 2023-05-01 17:51
 */
@Getter
@AllArgsConstructor
public enum MessageTemplateType {

    /**
     * 通知类消息
     */
    NOTICE_MESSAGE((byte) 1, "通知类消息"),

    /**
     * 营销类消息
     */
    MARKING_MESSAGE((byte) 2, "营销类消息"),

    /**
     * 营销类消息
     */
    CAPTCHA_MESSAGE((byte) 3, "验证码类消息"),

    ;

    private final Byte code;
    private final String desc;

    public static MessageTemplateType codeOf(Byte templateType) {
        if (Objects.isNull(templateType)) {
            return null;
        }
        return Arrays.stream(MessageTemplateType.values())
                .filter(config -> Objects.equals(config.getCode(), templateType))
                .findAny().orElse(null);
    }

}
