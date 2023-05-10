package top.easyblog.dao.auto.model;

import lombok.Data;

@Data
public class UserRoleRelationshipKey {
    private Long userId;

    private Long roleId;
}