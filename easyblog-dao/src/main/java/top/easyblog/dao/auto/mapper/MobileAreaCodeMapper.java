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
import top.easyblog.dao.auto.mapper.provider.MobileAreaCodeSqlProvider;
import top.easyblog.dao.auto.model.MobileAreaCode;
import top.easyblog.dao.auto.model.example.MobileAreaCodeExample;

@Mapper
@Repository
public interface MobileAreaCodeMapper {
    @SelectProvider(type= MobileAreaCodeSqlProvider.class, method="countByExample")
    long countByExample(MobileAreaCodeExample example);

    @DeleteProvider(type=MobileAreaCodeSqlProvider.class, method="deleteByExample")
    int deleteByExample(MobileAreaCodeExample example);

    @Delete({
        "delete from mobile_area_code",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into mobile_area_code (code, crown_code, ",
        "country_code, area_code, ",
        "area_name, create_time, ",
        "update_time)",
        "values (#{code,jdbcType=VARCHAR}, #{crownCode,jdbcType=VARCHAR}, ",
        "#{countryCode,jdbcType=VARCHAR}, #{areaCode,jdbcType=VARCHAR}, ",
        "#{areaName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(MobileAreaCode record);

    @InsertProvider(type=MobileAreaCodeSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(MobileAreaCode record);

    @SelectProvider(type=MobileAreaCodeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="crown_code", property="crownCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_code", property="countryCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_code", property="areaCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_name", property="areaName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MobileAreaCode> selectByExample(MobileAreaCodeExample example);

    @Select({
        "select",
        "id, code, crown_code, country_code, area_code, area_name, create_time, update_time",
        "from mobile_area_code",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="crown_code", property="crownCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="country_code", property="countryCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_code", property="areaCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_name", property="areaName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MobileAreaCode selectByPrimaryKey(Long id);

    @UpdateProvider(type=MobileAreaCodeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MobileAreaCode record, @Param("example") MobileAreaCodeExample example);

    @UpdateProvider(type=MobileAreaCodeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MobileAreaCode record, @Param("example") MobileAreaCodeExample example);

    @UpdateProvider(type=MobileAreaCodeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MobileAreaCode record);

    @Update({
        "update mobile_area_code",
        "set code = #{code,jdbcType=VARCHAR},",
          "crown_code = #{crownCode,jdbcType=VARCHAR},",
          "country_code = #{countryCode,jdbcType=VARCHAR},",
          "area_code = #{areaCode,jdbcType=VARCHAR},",
          "area_name = #{areaName,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MobileAreaCode record);
}