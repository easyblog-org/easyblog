package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class Roles {
    private Long id;

    private String code;

    private Short name;

    private String desc;

    private Boolean enabled;

    private Date createTime;

    private Date updateTime;
}