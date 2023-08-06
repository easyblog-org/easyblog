package top.easyblog.core.strategy;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.service.strategy.ICaptchaStrategy;
import top.easyblog.service.strategy.ILoginStrategy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-08-06 17:09
 */
@Slf4j
@Component
public class CaptchaStrategyContext {


    private static Map<String, ICaptchaStrategy> captchaStrategyMap = Maps.newHashMap();

    @Autowired
    private List<ICaptchaStrategy> captchaStrategies;

    public static ICaptchaStrategy getCaptchaStrategy(String captchaCodeType, Integer identifierType) {
        if (StringUtils.isBlank(captchaCodeType) || Objects.isNull(identifierType)) {
            throw new IllegalArgumentException(EasyResultCode.INCORRECT_CAPTCHA_POLICY.getCode());
        }

        ICaptchaStrategy captchaStrategy = captchaStrategyMap.get(String.format("%s-%s", StringUtils.upperCase(captchaCodeType), identifierType));
        IdentifierType type = IdentifierType.subCodeOf(identifierType);
        if (Objects.isNull(type) || Objects.equals(IdentifierType.UNKNOWN, type)) {
            throw new IllegalArgumentException(EasyResultCode.INCORRECT_CAPTCHA_POLICY.getCode());
        }
        return captchaStrategy;
    }

    @PostConstruct
    public void initLoginStrategyMap() {
        log.info("Start init captcha strategy.....");
        captchaStrategyMap = captchaStrategies.stream().collect(Collectors.toMap(ICaptchaStrategy::getCaptchaType, Function.identity(), (x, y) -> x));
        log.info("Init captcha strategy successfully!");
    }
}
