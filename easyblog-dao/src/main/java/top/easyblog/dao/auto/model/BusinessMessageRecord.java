package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class BusinessMessageRecord {
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