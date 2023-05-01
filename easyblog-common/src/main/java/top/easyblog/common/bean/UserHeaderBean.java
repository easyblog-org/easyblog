package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/01/30 10:32
 */
@Data
public class UserHeaderBean {
    private Long id;

    private String headerImgUrl;

    private String userCode;

    private Boolean isCurrentHeader;

    private Date createTime;

    private Date updateTime;
}
