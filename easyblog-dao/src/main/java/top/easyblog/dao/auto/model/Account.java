package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class Account {
    private Long id;

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