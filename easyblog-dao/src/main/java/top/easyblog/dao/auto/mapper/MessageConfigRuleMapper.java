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
import top.easyblog.dao.auto.mapper.provider.MessageConfigRuleSqlProvider;
import top.easyblog.dao.auto.model.MessageConfigRule;
import top.easyblog.dao.auto.model.example.MessageConfigRuleExample;

@Mapper
@Repository
public interface MessageConfigRuleMapper {
    @SelectProvider(type= MessageConfigRuleSqlProvider.class, method="countByExample")
    long countByExample(MessageConfigRuleExample example);

    @DeleteProvider(type=MessageConfigRuleSqlProvider.class, method="deleteByExample")
    int deleteByExample(MessageConfigRuleExample example);

    @Delete({
        "delete from message_config_rule",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into message_config_rule (business_module, business_event, ",
        "template_code, channel, ",
        "group, priority, ",
        "config_ids, deleted, ",
        "create_time, update_time)",
        "values (#{businessModule,jdbcType=VARCHAR}, #{businessEvent,jdbcType=VARCHAR}, ",
        "#{templateCode,jdbcType=VARCHAR}, #{channel,jdbcType=VARCHAR}, ",
        "#{group,jdbcType=VARCHAR}, #{priority,jdbcType=INTEGER}, ",
        "#{configIds,jdbcType=VARCHAR}, #{deleted,jdbcType=BIT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(MessageConfigRule record);

    @InsertProvider(type=MessageConfigRuleSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(MessageConfigRule record);

    @SelectProvider(type=MessageConfigRuleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="business_module", property="businessModule", jdbcType=JdbcType.VARCHAR),
        @Result(column="business_event", property="businessEvent", jdbcType=JdbcType.VARCHAR),
        @Result(column="template_code", property="templateCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.VARCHAR),
        @Result(column="group", property="group", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="config_ids", property="configIds", jdbcType=JdbcType.VARCHAR),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MessageConfigRule> selectByExample(MessageConfigRuleExample example);

    @Select({
        "select",
        "id, business_module, business_event, template_code, channel, group, priority, ",
        "config_ids, deleted, create_time, update_time",
        "from message_config_rule",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="business_module", property="businessModule", jdbcType=JdbcType.VARCHAR),
        @Result(column="business_event", property="businessEvent", jdbcType=JdbcType.VARCHAR),
        @Result(column="template_code", property="templateCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.VARCHAR),
        @Result(column="group", property="group", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="config_ids", property="configIds", jdbcType=JdbcType.VARCHAR),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MessageConfigRule selectByPrimaryKey(Long id);

    @UpdateProvider(type=MessageConfigRuleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MessageConfigRule record, @Param("example") MessageConfigRuleExample example);

    @UpdateProvider(type=MessageConfigRuleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MessageConfigRule record, @Param("example") MessageConfigRuleExample example);

    @UpdateProvider(type=MessageConfigRuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MessageConfigRule record);

    @Update({
        "update message_config_rule",
        "set business_module = #{businessModule,jdbcType=VARCHAR},",
          "business_event = #{businessEvent,jdbcType=VARCHAR},",
          "template_code = #{templateCode,jdbcType=VARCHAR},",
          "channel = #{channel,jdbcType=VARCHAR},",
          "group = #{group,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=INTEGER},",
          "config_ids = #{configIds,jdbcType=VARCHAR},",
          "deleted = #{deleted,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MessageConfigRule record);
}