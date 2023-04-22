package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: frank.huang
 * @date: 2021-10-31 23:40
 */
@Getter
@AllArgsConstructor
public enum Status {

    UNKNOWN((byte) -1),

    ENABLE((byte) 1),

    DISABLE((byte) 0);

    private final Byte code;

    public Status of(Byte code) {
        return Arrays.stream(Status.values()).filter(c -> c.getCode().equals(code)).findAny().orElse(null);
    }

}
