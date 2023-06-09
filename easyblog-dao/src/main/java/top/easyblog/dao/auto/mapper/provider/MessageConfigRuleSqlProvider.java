package top.easyblog.dao.auto.mapper.provider;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import top.easyblog.dao.auto.model.MessageConfigRule;
import top.easyblog.dao.auto.model.example.MessageConfigRuleExample.Criteria;
import top.easyblog.dao.auto.model.example.MessageConfigRuleExample.Criterion;
import top.easyblog.dao.auto.model.example.MessageConfigRuleExample;

public class MessageConfigRuleSqlProvider {

    public String countByExample(MessageConfigRuleExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("message_config_rule");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(MessageConfigRuleExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("message_config_rule");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(MessageConfigRule record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("message_config_rule");
        
        if (record.getBusinessModule() != null) {
            sql.VALUES("business_module", "#{businessModule,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessEvent() != null) {
            sql.VALUES("business_event", "#{businessEvent,jdbcType=VARCHAR}");
        }
        
        if (record.getTemplateCode() != null) {
            sql.VALUES("template_code", "#{templateCode,jdbcType=VARCHAR}");
        }
        
        if (record.getChannel() != null) {
            sql.VALUES("channel", "#{channel,jdbcType=TINYINT}");
        }
        
        if (record.getMsgGroup() != null) {
            sql.VALUES("msg_group", "#{msgGroup,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.VALUES("priority", "#{priority,jdbcType=INTEGER}");
        }
        
        if (record.getConfigIds() != null) {
            sql.VALUES("config_ids", "#{configIds,jdbcType=VARCHAR}");
        }
        
        if (record.getDeleted() != null) {
            sql.VALUES("deleted", "#{deleted,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(MessageConfigRuleExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("business_module");
        sql.SELECT("business_event");
        sql.SELECT("template_code");
        sql.SELECT("channel");
        sql.SELECT("msg_group");
        sql.SELECT("priority");
        sql.SELECT("config_ids");
        sql.SELECT("deleted");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("message_config_rule");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }

        StringBuilder sqlBuilder = new StringBuilder(sql.toString());
        if (example != null && example.getOffset() != null && example.getLimit() >= 0) {
            sqlBuilder.append(" LIMIT ").append(example.getOffset());
            if (example.getLimit() != null && example.getLimit() > 0) {
                sqlBuilder.append(",").append(example.getLimit());
            }
        }
        return sqlBuilder.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        MessageConfigRule record = (MessageConfigRule) parameter.get("record");
        MessageConfigRuleExample example = (MessageConfigRuleExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("message_config_rule");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getBusinessModule() != null) {
            sql.SET("business_module = #{record.businessModule,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessEvent() != null) {
            sql.SET("business_event = #{record.businessEvent,jdbcType=VARCHAR}");
        }
        
        if (record.getTemplateCode() != null) {
            sql.SET("template_code = #{record.templateCode,jdbcType=VARCHAR}");
        }
        
        if (record.getChannel() != null) {
            sql.SET("channel = #{record.channel,jdbcType=TINYINT}");
        }
        
        if (record.getMsgGroup() != null) {
            sql.SET("msg_group = #{record.msgGroup,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        }
        
        if (record.getConfigIds() != null) {
            sql.SET("config_ids = #{record.configIds,jdbcType=VARCHAR}");
        }
        
        if (record.getDeleted() != null) {
            sql.SET("deleted = #{record.deleted,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("message_config_rule");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("business_module = #{record.businessModule,jdbcType=VARCHAR}");
        sql.SET("business_event = #{record.businessEvent,jdbcType=VARCHAR}");
        sql.SET("template_code = #{record.templateCode,jdbcType=VARCHAR}");
        sql.SET("channel = #{record.channel,jdbcType=TINYINT}");
        sql.SET("msg_group = #{record.msgGroup,jdbcType=VARCHAR}");
        sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        sql.SET("config_ids = #{record.configIds,jdbcType=VARCHAR}");
        sql.SET("deleted = #{record.deleted,jdbcType=BIT}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        MessageConfigRuleExample example = (MessageConfigRuleExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MessageConfigRule record) {
        SQL sql = new SQL();
        sql.UPDATE("message_config_rule");
        
        if (record.getBusinessModule() != null) {
            sql.SET("business_module = #{businessModule,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessEvent() != null) {
            sql.SET("business_event = #{businessEvent,jdbcType=VARCHAR}");
        }
        
        if (record.getTemplateCode() != null) {
            sql.SET("template_code = #{templateCode,jdbcType=VARCHAR}");
        }
        
        if (record.getChannel() != null) {
            sql.SET("channel = #{channel,jdbcType=TINYINT}");
        }
        
        if (record.getMsgGroup() != null) {
            sql.SET("msg_group = #{msgGroup,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.SET("priority = #{priority,jdbcType=INTEGER}");
        }
        
        if (record.getConfigIds() != null) {
            sql.SET("config_ids = #{configIds,jdbcType=VARCHAR}");
        }
        
        if (record.getDeleted() != null) {
            sql.SET("deleted = #{deleted,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, MessageConfigRuleExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}