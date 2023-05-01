package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 消息模板状态
 *
 * @author: frank.huang
 * @date: 2023-05-01 17:51
 */
@Getter
@AllArgsConstructor
public enum MessageTemplateStatus {

    /**
     * 草稿
     */
    DRAFT((byte) 1),

    /**
     * 已发布
     */
    RELEASE((byte) 2),


    ;

    private final Byte code;


    public static MessageTemplateStatus codeOf(Byte status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return Arrays.stream(MessageTemplateStatus.values()).filter(config -> Objects.equals(config.getCode(), status))
                .findAny().orElse(null);
    }

}
