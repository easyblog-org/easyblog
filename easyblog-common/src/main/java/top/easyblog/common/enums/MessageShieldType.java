package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-05-01 17:51
 */
@Getter
@AllArgsConstructor
public enum MessageShieldType {

    /**
     * 夜间不屏蔽
     */
    NO_SHIELD((byte) 1, "夜间不屏蔽"),

    /**
     * 夜间屏蔽
     */
    SHIELD((byte) 2, "夜间屏蔽"),


    ;

    private final Byte code;
    private final String desc;


    public static MessageShieldType codeOf(Byte shieldType) {
        if (Objects.isNull(shieldType)) {
            return null;
        }
        return Arrays.stream(MessageShieldType.values()).filter(config -> Objects.equals(config.getCode(), shieldType))
                .findAny().orElse(null);
    }
}
