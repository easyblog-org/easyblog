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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import top.easyblog.dao.auto.mapper.provider.AccountSqlProvider;
import top.easyblog.dao.auto.model.Account;
import top.easyblog.dao.auto.model.example.AccountExample;

@Mapper
@Repository
public interface AccountMapper {
    @SelectProvider(type = AccountSqlProvider.class, method = "countByExample")
    long countByExample(AccountExample example);

    @DeleteProvider(type = AccountSqlProvider.class, method = "deleteByExample")
    int deleteByExample(AccountExample example);

    @Delete({
            "delete from account",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into account (code, user_code, ",
            "identity_type, identifier, ",
            "credential, verified, ",
            "status, create_time, ",
            "update_time)",
            "values (#{code,jdbcType=VARCHAR}, #{userCode,jdbcType=VARCHAR}, ",
            "#{identityType,jdbcType=INTEGER}, #{identifier,jdbcType=VARCHAR}, ",
            "#{credential,jdbcType=VARCHAR}, #{verified,jdbcType=INTEGER}, ",
            "#{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Account record);

    @InsertProvider(type = AccountSqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insertSelective(Account record);

    @SelectProvider(type = AccountSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_code", property = "userCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "identity_type", property = "identityType", jdbcType = JdbcType.INTEGER),
            @Result(column = "identifier", property = "identifier", jdbcType = JdbcType.VARCHAR),
            @Result(column = "credential", property = "credential", jdbcType = JdbcType.VARCHAR),
            @Result(column = "verified", property = "verified", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Account> selectByExample(AccountExample example);

    @Select({
            "select",
            "id, code, user_code, identity_type, identifier, credential, verified, status, ",
            "create_time, update_time",
            "from account",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_code", property = "userCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "identity_type", property = "identityType", jdbcType = JdbcType.INTEGER),
            @Result(column = "identifier", property = "identifier", jdbcType = JdbcType.VARCHAR),
            @Result(column = "credential", property = "credential", jdbcType = JdbcType.VARCHAR),
            @Result(column = "verified", property = "verified", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    Account selectByPrimaryKey(Long id);

    @UpdateProvider(type = AccountSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    @UpdateProvider(type = AccountSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    @UpdateProvider(type = AccountSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Account record);

    @Update({
            "update account",
            "set code = #{code,jdbcType=VARCHAR},",
            "user_code = #{userCode,jdbcType=VARCHAR},",
            "identity_type = #{identityType,jdbcType=INTEGER},",
            "identifier = #{identifier,jdbcType=VARCHAR},",
            "credential = #{credential,jdbcType=VARCHAR},",
            "verified = #{verified,jdbcType=INTEGER},",
            "status = #{status,jdbcType=INTEGER},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Account record);
}