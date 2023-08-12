package top.easyblog.common.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-05 11:51
 */
@Getter
@AllArgsConstructor
public enum TemplateValueConfigType {
    /**
     * 直接值
     */
    DIRECT_VALUE((byte) 1, "直接值"),
    /**
     * JSON 直接值
     */
    DIRECT_JSON_VALUE((byte) 2, "Json直接值"),
    /**
     * 接口直接值
     */
    INTERFACE_DIRECT_VALUE((byte) 3, "接口直接值"),
    /**
     * 接口 JSON 值
     */
    INTERFACE_JSON_VALUE((byte) 4, "接口Json取值"),

    ;

    private final byte code;
    private final String desc;

    // 直接取值消息类型
    public static final List<Byte> DIRECT_VALUE_TYPES = Collections.unmodifiableList(Lists.newArrayList(
            DIRECT_VALUE.getCode(),
            DIRECT_JSON_VALUE.getCode()
    ));

    public static TemplateValueConfigType codeOf(Byte configType) {
        if (Objects.isNull(configType)) {
            return null;
        }
        return Arrays.stream(TemplateValueConfigType.values()).filter(config -> Objects.equals(config.getCode(), configType))
                .findAny().orElse(null);
    }
}
