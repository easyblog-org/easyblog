package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class UserRoleRelationship {
    private Long userId;

    private Long roleId;

    private Boolean enabled;

    private Date createTime;

    private Date updateTime;
}