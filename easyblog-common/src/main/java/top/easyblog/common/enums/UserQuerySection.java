package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: frank.huang
 * @date: 2023-04-22 22:00
 */
@Getter
@AllArgsConstructor
public enum UserQuerySection {
    QUERY_HEADER_IMG("header_img"),

    QUERY_CURRENT_HEADER_IMG("current_header_img"),

    QUERY_ACCOUNTS("accounts"),

    QUERY_SIGN_LOG("sign_log"),

    QUERY_ROLE("roles")
    ;

    private String name;
}
