package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRoleRelationship extends UserRoleRelationshipKey {
    private Boolean enabled;

    private Date createTime;

    private Date updateTime;
}