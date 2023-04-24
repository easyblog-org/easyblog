package top.easyblog.dao.auto.mapper.provider;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import top.easyblog.dao.auto.model.BusinessMessageRecord;
import top.easyblog.dao.auto.model.example.BusinessMessageRecordExample.Criteria;
import top.easyblog.dao.auto.model.example.BusinessMessageRecordExample.Criterion;
import top.easyblog.dao.auto.model.example.BusinessMessageRecordExample;

public class BusinessMessageRecordSqlProvider {

    public String countByExample(BusinessMessageRecordExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("business_message_record");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(BusinessMessageRecordExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("business_message_record");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(BusinessMessageRecord record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("business_message_record");
        
        if (record.getBusinessId() != null) {
            sql.VALUES("business_id", "#{businessId,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessModule() != null) {
            sql.VALUES("business_module", "#{businessModule,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessEvent() != null) {
            sql.VALUES("business_event", "#{businessEvent,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=TINYINT}");
        }
        
        if (record.getRetryTimes() != null) {
            sql.VALUES("retry_times", "#{retryTimes,jdbcType=INTEGER}");
        }
        
        if (record.getFailReason() != null) {
            sql.VALUES("fail_reason", "#{failReason,jdbcType=VARCHAR}");
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
        
        if (record.getBusinessMessage() != null) {
            sql.VALUES("business_message", "#{businessMessage,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExampleWithBLOBs(BusinessMessageRecordExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("business_id");
        sql.SELECT("business_module");
        sql.SELECT("business_event");
        sql.SELECT("status");
        sql.SELECT("retry_times");
        sql.SELECT("fail_reason");
        sql.SELECT("deleted");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.SELECT("business_message");
        sql.FROM("business_message_record");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(BusinessMessageRecordExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("business_id");
        sql.SELECT("business_module");
        sql.SELECT("business_event");
        sql.SELECT("status");
        sql.SELECT("retry_times");
        sql.SELECT("fail_reason");
        sql.SELECT("deleted");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("business_message_record");
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
        BusinessMessageRecord record = (BusinessMessageRecord) parameter.get("record");
        BusinessMessageRecordExample example = (BusinessMessageRecordExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("business_message_record");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getBusinessId() != null) {
            sql.SET("business_id = #{record.businessId,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessModule() != null) {
            sql.SET("business_module = #{record.businessModule,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessEvent() != null) {
            sql.SET("business_event = #{record.businessEvent,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=TINYINT}");
        }
        
        if (record.getRetryTimes() != null) {
            sql.SET("retry_times = #{record.retryTimes,jdbcType=INTEGER}");
        }
        
        if (record.getFailReason() != null) {
            sql.SET("fail_reason = #{record.failReason,jdbcType=VARCHAR}");
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
        
        if (record.getBusinessMessage() != null) {
            sql.SET("business_message = #{record.businessMessage,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("business_message_record");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("business_id = #{record.businessId,jdbcType=VARCHAR}");
        sql.SET("business_module = #{record.businessModule,jdbcType=VARCHAR}");
        sql.SET("business_event = #{record.businessEvent,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=TINYINT}");
        sql.SET("retry_times = #{record.retryTimes,jdbcType=INTEGER}");
        sql.SET("fail_reason = #{record.failReason,jdbcType=VARCHAR}");
        sql.SET("deleted = #{record.deleted,jdbcType=BIT}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        sql.SET("business_message = #{record.businessMessage,jdbcType=LONGVARCHAR}");
        
        BusinessMessageRecordExample example = (BusinessMessageRecordExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("business_message_record");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("business_id = #{record.businessId,jdbcType=VARCHAR}");
        sql.SET("business_module = #{record.businessModule,jdbcType=VARCHAR}");
        sql.SET("business_event = #{record.businessEvent,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=TINYINT}");
        sql.SET("retry_times = #{record.retryTimes,jdbcType=INTEGER}");
        sql.SET("fail_reason = #{record.failReason,jdbcType=VARCHAR}");
        sql.SET("deleted = #{record.deleted,jdbcType=BIT}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        BusinessMessageRecordExample example = (BusinessMessageRecordExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(BusinessMessageRecord record) {
        SQL sql = new SQL();
        sql.UPDATE("business_message_record");
        
        if (record.getBusinessId() != null) {
            sql.SET("business_id = #{businessId,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessModule() != null) {
            sql.SET("business_module = #{businessModule,jdbcType=VARCHAR}");
        }
        
        if (record.getBusinessEvent() != null) {
            sql.SET("business_event = #{businessEvent,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=TINYINT}");
        }
        
        if (record.getRetryTimes() != null) {
            sql.SET("retry_times = #{retryTimes,jdbcType=INTEGER}");
        }
        
        if (record.getFailReason() != null) {
            sql.SET("fail_reason = #{failReason,jdbcType=VARCHAR}");
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
        
        if (record.getBusinessMessage() != null) {
            sql.SET("business_message = #{businessMessage,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, BusinessMessageRecordExample example, boolean includeExamplePhrase) {
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