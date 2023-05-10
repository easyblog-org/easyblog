package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class LoginLog {
    private Long id;

    private String code;

    private String userCode;

    private String accountCode;

    private String token;

    private Integer status;

    private String ipAddress;

    private String device;

    private String operationSystem;

    private String location;

    private Date createTime;

    private Date updateTime;
}