package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private Long id;

    private String code;

    private String nickName;

    private Integer integration;

    private Integer level;

    private Integer visit;

    private Integer active;

    private String introduction;

    private Date createTime;

    private Date updateTime;
}