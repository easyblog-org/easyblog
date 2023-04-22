package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/01/30 10:31
 */
@Data
public class AccountBean {

    private Long userId;

    private String code;

    private String userCode;

    private Integer identityType;

    private String identifier;

    private String credential;

    private Integer verified;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
