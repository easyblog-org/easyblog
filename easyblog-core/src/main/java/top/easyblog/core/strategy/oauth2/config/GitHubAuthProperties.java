package top.easyblog.core.strategy.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author frank.huang
 * @date 2022/02/21 16:50
 */
@Component
@ConfigurationProperties(prefix = "oauth.github")
public class GitHubAuthProperties extends AuthProperties {
   
}
