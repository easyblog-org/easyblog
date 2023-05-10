package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2023-04-29 19:01
 */
@Data
public class BusinessMessageRecordBean {
    private Long id;

    private String businessId;

    private String businessModule;

    private String businessEvent;

    private Byte status;

    private Integer retryTimes;

    private String failReason;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    private String businessMessage;
}
