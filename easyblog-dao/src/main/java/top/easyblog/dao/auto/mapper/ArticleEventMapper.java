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
import top.easyblog.dao.auto.mapper.provider.ArticleEventSqlProvider;
import top.easyblog.dao.auto.model.ArticleEvent;
import top.easyblog.dao.auto.model.example.ArticleEventExample;

@Mapper
@Repository
public interface ArticleEventMapper {
    @SelectProvider(type= ArticleEventSqlProvider.class, method="countByExample")
    long countByExample(ArticleEventExample example);

    @DeleteProvider(type=ArticleEventSqlProvider.class, method="deleteByExample")
    int deleteByExample(ArticleEventExample example);

    @Delete({
        "delete from article_event",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into article_event (article_code, user_code, ",
        "event, operator, ",
        "create_time, update_time, ",
        "remark)",
        "values (#{articleCode,jdbcType=VARCHAR}, #{userCode,jdbcType=VARCHAR}, ",
        "#{event,jdbcType=VARCHAR}, #{operator,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(ArticleEvent record);

    @InsertProvider(type=ArticleEventSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(ArticleEvent record);

    @SelectProvider(type=ArticleEventSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="article_code", property="articleCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="event", property="event", jdbcType=JdbcType.VARCHAR),
        @Result(column="operator", property="operator", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ArticleEvent> selectByExampleWithBLOBs(ArticleEventExample example);

    @SelectProvider(type=ArticleEventSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="article_code", property="articleCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="event", property="event", jdbcType=JdbcType.VARCHAR),
        @Result(column="operator", property="operator", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ArticleEvent> selectByExample(ArticleEventExample example);

    @Select({
        "select",
        "id, article_code, user_code, event, operator, create_time, update_time, remark",
        "from article_event",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="article_code", property="articleCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="event", property="event", jdbcType=JdbcType.VARCHAR),
        @Result(column="operator", property="operator", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    ArticleEvent selectByPrimaryKey(Long id);

    @UpdateProvider(type=ArticleEventSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ArticleEvent record, @Param("example") ArticleEventExample example);

    @UpdateProvider(type=ArticleEventSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") ArticleEvent record, @Param("example") ArticleEventExample example);

    @UpdateProvider(type=ArticleEventSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ArticleEvent record, @Param("example") ArticleEventExample example);

    @UpdateProvider(type=ArticleEventSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ArticleEvent record);

    @Update({
        "update article_event",
        "set article_code = #{articleCode,jdbcType=VARCHAR},",
          "user_code = #{userCode,jdbcType=VARCHAR},",
          "event = #{event,jdbcType=VARCHAR},",
          "operator = #{operator,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(ArticleEvent record);

    @Update({
        "update article_event",
        "set article_code = #{articleCode,jdbcType=VARCHAR},",
          "user_code = #{userCode,jdbcType=VARCHAR},",
          "event = #{event,jdbcType=VARCHAR},",
          "operator = #{operator,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ArticleEvent record);
}