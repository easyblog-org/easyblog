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
public enum MessageSendStatus {

    /**
     * 已初始化
     */
    UNSEND((byte) 1),
    /**
     * 发送成功
     */
    SUCCESS((byte) 2),
    /**
     * 发送失败
     */
    FAILED((byte) 3),

    ;

    private final byte code;


    public static MessageSendStatus codeOf(Byte code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.stream(MessageSendStatus.values())
                .filter(channel -> Objects.equals(channel.getCode(), code))
                .findAny().orElse(null);
    }
}
