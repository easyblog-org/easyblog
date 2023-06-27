package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;

import java.util.Arrays;
import java.util.Optional;

/**
 * 洲枚举
 *
 * @author: frank.huang
 * @date: 2023-03-12 15:39
 */
@Getter
@AllArgsConstructor
public enum ContinentEnum {
    GLOBAL(null, "全部"),
    AFRICA("AF", "Africa"),
    ANTARCTICA("AN", "Antarctica"),
    ASIA("AS", "Asia"),
    AUSTRALIA("AU", "Australia"),
    EUROPE("EU", "Europe"),
    NORTH_AMERICA("NA", "North America"),
    SOUTH_AMERICA("SA", "South America");

    private final String code;
    private final String desc;

    public static Optional<ContinentEnum> codeOfOptional(String code) {
        if (StringUtils.isBlank(code)) throw new BusinessException(EasyResultCode.UNKNOWN_CONTINENT_CODE);
        Optional<ContinentEnum> enumOptional = Arrays.stream(ContinentEnum.values()).filter(item -> StringUtils.equalsIgnoreCase(item.getCode(), code)).findAny();
        if (!enumOptional.isPresent()) {
            throw new BusinessException(EasyResultCode.UNKNOWN_CONTINENT_CODE);
        }
        return enumOptional;
    }

}
