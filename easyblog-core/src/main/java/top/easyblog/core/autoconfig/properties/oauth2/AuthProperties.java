package top.easyblog.core.autoconfig.properties.oauth2;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-03-06 11:59
 */
@Data
public class AuthProperties {
    private String clientId;
    private String clientSecret;
    private String authorizeUrl;
    protected String redirectUrl;
}
