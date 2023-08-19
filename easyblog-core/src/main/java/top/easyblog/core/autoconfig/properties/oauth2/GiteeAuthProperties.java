package top.easyblog.core.autoconfig.properties.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: frank.huang
 * @date: 2022-03-06 12:17
 */
@Component
@ConfigurationProperties(prefix = "oauth.gitee")
public class GiteeAuthProperties extends AuthProperties {
}
