package top.easyblog.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-25 14:15
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "easyblog.auth")
public class AuthProperties {
    /**
     * 认证白名单
     */
    private List<String> whiteList;
}
