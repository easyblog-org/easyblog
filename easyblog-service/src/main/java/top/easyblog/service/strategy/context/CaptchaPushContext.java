package top.easyblog.service.strategy.context;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2023-08-06 16:48
 */
@Data
@Builder
public class CaptchaPushContext {
    private String captchaCodeType;
    private Integer identifierType;
    private String identifier;
    private String captchaCode;
}
