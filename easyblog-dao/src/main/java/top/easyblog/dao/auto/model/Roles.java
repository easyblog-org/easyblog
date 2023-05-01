package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class Roles {
    private Long id;

    private String code;

    private String name;

    private String description;

    private Boolean enabled;

    private Date createTime;

    private Date updateTime;
}