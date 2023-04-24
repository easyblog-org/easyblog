package top.easyblog.dao.auto.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import top.easyblog.dao.auto.mapper.provider.UserRoleRelationshipSqlProvider;
import top.easyblog.dao.auto.model.UserRoleRelationship;
import top.easyblog.dao.auto.model.example.UserRoleRelationshipExample;
import top.easyblog.dao.auto.model.UserRoleRelationshipKey;

@Mapper
@Repository
public interface UserRoleRelationshipMapper {
    @SelectProvider(type= UserRoleRelationshipSqlProvider.class, method="countByExample")
    long countByExample(UserRoleRelationshipExample example);

    @DeleteProvider(type=UserRoleRelationshipSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserRoleRelationshipExample example);

    @Delete({
        "delete from user_role_relationship",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(UserRoleRelationshipKey key);

    @Insert({
        "insert into user_role_relationship (user_id, role_id, ",
        "enabled, create_time, ",
        "update_time)",
        "values (#{userId,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{enabled,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserRoleRelationship record);

    @InsertProvider(type=UserRoleRelationshipSqlProvider.class, method="insertSelective")
    int insertSelective(UserRoleRelationship record);

    @SelectProvider(type=UserRoleRelationshipSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserRoleRelationship> selectByExample(UserRoleRelationshipExample example);

    @Select({
        "select",
        "user_id, role_id, enabled, create_time, update_time",
        "from user_role_relationship",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserRoleRelationship selectByPrimaryKey(UserRoleRelationshipKey key);

    @UpdateProvider(type=UserRoleRelationshipSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserRoleRelationship record, @Param("example") UserRoleRelationshipExample example);

    @UpdateProvider(type=UserRoleRelationshipSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserRoleRelationship record, @Param("example") UserRoleRelationshipExample example);

    @UpdateProvider(type=UserRoleRelationshipSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserRoleRelationship record);

    @Update({
        "update user_role_relationship",
        "set enabled = #{enabled,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRoleRelationship record);
}