package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class UserRoleRelationship {
    private Integer userId;

    private Integer roleId;

    private Boolean enabled;

    private Date createTime;

    private Date updateTime;
}