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
import top.easyblog.dao.auto.mapper.provider.PhoneAuthSqlProvider;
import top.easyblog.dao.auto.model.PhoneAuth;
import top.easyblog.dao.auto.model.example.PhoneAuthExample;

@Mapper
@Repository
public interface PhoneAuthMapper {
    @SelectProvider(type= PhoneAuthSqlProvider.class, method="countByExample")
    long countByExample(PhoneAuthExample example);

    @DeleteProvider(type=PhoneAuthSqlProvider.class, method="deleteByExample")
    int deleteByExample(PhoneAuthExample example);

    @Delete({
        "delete from phone_auth",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into phone_auth (code, mobile_area_code_id, ",
        "phone, create_time, ",
        "update_time)",
        "values (#{code,jdbcType=VARCHAR}, #{mobileAreaCodeId,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(PhoneAuth record);

    @InsertProvider(type=PhoneAuthSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(PhoneAuth record);

    @SelectProvider(type=PhoneAuthSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="mobile_area_code_id", property="mobileAreaCodeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PhoneAuth> selectByExample(PhoneAuthExample example);

    @Select({
        "select",
        "id, code, mobile_area_code_id, phone, create_time, update_time",
        "from phone_auth",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="mobile_area_code_id", property="mobileAreaCodeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PhoneAuth selectByPrimaryKey(Long id);

    @UpdateProvider(type=PhoneAuthSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PhoneAuth record, @Param("example") PhoneAuthExample example);

    @UpdateProvider(type=PhoneAuthSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PhoneAuth record, @Param("example") PhoneAuthExample example);

    @UpdateProvider(type=PhoneAuthSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PhoneAuth record);

    @Update({
        "update phone_auth",
        "set code = #{code,jdbcType=VARCHAR},",
          "mobile_area_code_id = #{mobileAreaCodeId,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PhoneAuth record);
}