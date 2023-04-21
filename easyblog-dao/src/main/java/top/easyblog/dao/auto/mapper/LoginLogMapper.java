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
import top.easyblog.dao.auto.model.LoginLog;
import top.easyblog.dao.auto.model.LoginLogExample;

@Mapper
@Repository
public interface LoginLogMapper {
    @SelectProvider(type=LoginLogSqlProvider.class, method="countByExample")
    long countByExample(LoginLogExample example);

    @DeleteProvider(type=LoginLogSqlProvider.class, method="deleteByExample")
    int deleteByExample(LoginLogExample example);

    @Delete({
        "delete from login_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into login_log (code, user_code, ",
        "account_code, token, ",
        "status, ip_address, ",
        "device, operation_system, ",
        "location, create_time, ",
        "update_time)",
        "values (#{code,jdbcType=VARCHAR}, #{userCode,jdbcType=VARCHAR}, ",
        "#{accountCode,jdbcType=VARCHAR}, #{token,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{ipAddress,jdbcType=VARCHAR}, ",
        "#{device,jdbcType=VARCHAR}, #{operationSystem,jdbcType=VARCHAR}, ",
        "#{location,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(LoginLog record);

    @InsertProvider(type=LoginLogSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(LoginLog record);

    @SelectProvider(type=LoginLogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="account_code", property="accountCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="token", property="token", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="ip_address", property="ipAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
        @Result(column="operation_system", property="operationSystem", jdbcType=JdbcType.VARCHAR),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<LoginLog> selectByExample(LoginLogExample example);

    @Select({
        "select",
        "id, code, user_code, account_code, token, status, ip_address, device, operation_system, ",
        "location, create_time, update_time",
        "from login_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="account_code", property="accountCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="token", property="token", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="ip_address", property="ipAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
        @Result(column="operation_system", property="operationSystem", jdbcType=JdbcType.VARCHAR),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    LoginLog selectByPrimaryKey(Long id);

    @UpdateProvider(type=LoginLogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") LoginLog record, @Param("example") LoginLogExample example);

    @UpdateProvider(type=LoginLogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") LoginLog record, @Param("example") LoginLogExample example);

    @UpdateProvider(type=LoginLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(LoginLog record);

    @Update({
        "update login_log",
        "set code = #{code,jdbcType=VARCHAR},",
          "user_code = #{userCode,jdbcType=VARCHAR},",
          "account_code = #{accountCode,jdbcType=VARCHAR},",
          "token = #{token,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "ip_address = #{ipAddress,jdbcType=VARCHAR},",
          "device = #{device,jdbcType=VARCHAR},",
          "operation_system = #{operationSystem,jdbcType=VARCHAR},",
          "location = #{location,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(LoginLog record);
}