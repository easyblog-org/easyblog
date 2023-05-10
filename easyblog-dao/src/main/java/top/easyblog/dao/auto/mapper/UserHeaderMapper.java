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
import top.easyblog.dao.auto.mapper.provider.UserHeaderSqlProvider;
import top.easyblog.dao.auto.model.UserHeader;
import top.easyblog.dao.auto.model.example.UserHeaderExample;

@Mapper
@Repository
public interface UserHeaderMapper {
    @SelectProvider(type= UserHeaderSqlProvider.class, method="countByExample")
    long countByExample(UserHeaderExample example);

    @DeleteProvider(type=UserHeaderSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserHeaderExample example);

    @Delete({
        "delete from user_header",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_header (code, header_img_url, ",
        "user_code, status, ",
        "create_time, update_time)",
        "values (#{code,jdbcType=VARCHAR}, #{headerImgUrl,jdbcType=VARCHAR}, ",
        "#{userCode,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(UserHeader record);

    @InsertProvider(type=UserHeaderSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(UserHeader record);

    @SelectProvider(type=UserHeaderSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="header_img_url", property="headerImgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserHeader> selectByExample(UserHeaderExample example);

    @Select({
        "select",
        "id, code, header_img_url, user_code, status, create_time, update_time",
        "from user_header",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="header_img_url", property="headerImgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserHeader selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserHeaderSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserHeader record, @Param("example") UserHeaderExample example);

    @UpdateProvider(type=UserHeaderSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserHeader record, @Param("example") UserHeaderExample example);

    @UpdateProvider(type=UserHeaderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserHeader record);

    @Update({
        "update user_header",
        "set code = #{code,jdbcType=VARCHAR},",
          "header_img_url = #{headerImgUrl,jdbcType=VARCHAR},",
          "user_code = #{userCode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserHeader record);
}