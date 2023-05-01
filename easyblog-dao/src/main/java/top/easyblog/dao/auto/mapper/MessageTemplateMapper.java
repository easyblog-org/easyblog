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
import top.easyblog.dao.auto.mapper.provider.MessageTemplateSqlProvider;
import top.easyblog.dao.auto.model.MessageTemplate;
import top.easyblog.dao.auto.model.example.MessageTemplateExample;

@Mapper
@Repository
public interface MessageTemplateMapper {
    @SelectProvider(type= MessageTemplateSqlProvider.class, method="countByExample")
    long countByExample(MessageTemplateExample example);

    @DeleteProvider(type=MessageTemplateSqlProvider.class, method="deleteByExample")
    int deleteByExample(MessageTemplateExample example);

    @Delete({
        "delete from message_template",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into message_template (template_code, name, ",
        "status, expect_push_time, ",
        "msg_type, shield_type, ",
        "msg_content, deleted, ",
        "create_time, update_time)",
        "values (#{templateCode,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=SMALLINT}, #{expectPushTime,jdbcType=VARCHAR}, ",
        "#{msgType,jdbcType=TINYINT}, #{shieldType,jdbcType=TINYINT}, ",
        "#{msgContent,jdbcType=VARCHAR}, #{deleted,jdbcType=BIT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(MessageTemplate record);

    @InsertProvider(type=MessageTemplateSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(MessageTemplate record);

    @SelectProvider(type=MessageTemplateSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="template_code", property="templateCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
        @Result(column="expect_push_time", property="expectPushTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="msg_type", property="msgType", jdbcType=JdbcType.TINYINT),
        @Result(column="shield_type", property="shieldType", jdbcType=JdbcType.TINYINT),
        @Result(column="msg_content", property="msgContent", jdbcType=JdbcType.VARCHAR),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MessageTemplate> selectByExample(MessageTemplateExample example);

    @Select({
        "select",
        "id, template_code, name, status, expect_push_time, msg_type, shield_type, msg_content, ",
        "deleted, create_time, update_time",
        "from message_template",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="template_code", property="templateCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
        @Result(column="expect_push_time", property="expectPushTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="msg_type", property="msgType", jdbcType=JdbcType.TINYINT),
        @Result(column="shield_type", property="shieldType", jdbcType=JdbcType.TINYINT),
        @Result(column="msg_content", property="msgContent", jdbcType=JdbcType.VARCHAR),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MessageTemplate selectByPrimaryKey(Long id);

    @UpdateProvider(type=MessageTemplateSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MessageTemplate record, @Param("example") MessageTemplateExample example);

    @UpdateProvider(type=MessageTemplateSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MessageTemplate record, @Param("example") MessageTemplateExample example);

    @UpdateProvider(type=MessageTemplateSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MessageTemplate record);

    @Update({
        "update message_template",
        "set template_code = #{templateCode,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=SMALLINT},",
          "expect_push_time = #{expectPushTime,jdbcType=VARCHAR},",
          "msg_type = #{msgType,jdbcType=TINYINT},",
          "shield_type = #{shieldType,jdbcType=TINYINT},",
          "msg_content = #{msgContent,jdbcType=VARCHAR},",
          "deleted = #{deleted,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MessageTemplate record);
}