package top.easyblog.dao.auto.mapper.provider;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import top.easyblog.dao.auto.model.Article;
import top.easyblog.dao.auto.model.example.ArticleExample.Criteria;
import top.easyblog.dao.auto.model.example.ArticleExample.Criterion;
import top.easyblog.dao.auto.model.example.ArticleExample;

public class ArticleSqlProvider {

    public String countByExample(ArticleExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("article");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(ArticleExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("article");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Article record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("article");
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorId() != null) {
            sql.VALUES("author_id", "#{authorId,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getCategory() != null) {
            sql.VALUES("category", "#{category,jdbcType=VARCHAR}");
        }
        
        if (record.getFeaturedImage() != null) {
            sql.VALUES("featured_image", "#{featuredImage,jdbcType=VARCHAR}");
        }
        
        if (record.getContentId() != null) {
            sql.VALUES("content_id", "#{contentId,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getIsTop() != null) {
            sql.VALUES("is_top", "#{isTop,jdbcType=BIT}");
        }
        
        if (record.getLikesNum() != null) {
            sql.VALUES("likes_num", "#{likesNum,jdbcType=INTEGER}");
        }
        
        if (record.getFavoritesNum() != null) {
            sql.VALUES("favorites_num", "#{favoritesNum,jdbcType=INTEGER}");
        }
        
        if (record.getRetweetsNum() != null) {
            sql.VALUES("retweets_num", "#{retweetsNum,jdbcType=INTEGER}");
        }
        
        if (record.getReportsNum() != null) {
            sql.VALUES("reports_num", "#{reportsNum,jdbcType=INTEGER}");
        }
        
        if (record.getPageViews() != null) {
            sql.VALUES("page_views", "#{pageViews,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(ArticleExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("code");
        sql.SELECT("author_id");
        sql.SELECT("title");
        sql.SELECT("category");
        sql.SELECT("featured_image");
        sql.SELECT("content_id");
        sql.SELECT("status");
        sql.SELECT("is_top");
        sql.SELECT("likes_num");
        sql.SELECT("favorites_num");
        sql.SELECT("retweets_num");
        sql.SELECT("reports_num");
        sql.SELECT("page_views");
        sql.SELECT("update_time");
        sql.SELECT("create_time");
        sql.FROM("article");
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
        Article record = (Article) parameter.get("record");
        ArticleExample example = (ArticleExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("article");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getCode() != null) {
            sql.SET("code = #{record.code,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorId() != null) {
            sql.SET("author_id = #{record.authorId,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.SET("title = #{record.title,jdbcType=VARCHAR}");
        }
        
        if (record.getCategory() != null) {
            sql.SET("category = #{record.category,jdbcType=VARCHAR}");
        }
        
        if (record.getFeaturedImage() != null) {
            sql.SET("featured_image = #{record.featuredImage,jdbcType=VARCHAR}");
        }
        
        if (record.getContentId() != null) {
            sql.SET("content_id = #{record.contentId,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=VARCHAR}");
        }
        
        if (record.getIsTop() != null) {
            sql.SET("is_top = #{record.isTop,jdbcType=BIT}");
        }
        
        if (record.getLikesNum() != null) {
            sql.SET("likes_num = #{record.likesNum,jdbcType=INTEGER}");
        }
        
        if (record.getFavoritesNum() != null) {
            sql.SET("favorites_num = #{record.favoritesNum,jdbcType=INTEGER}");
        }
        
        if (record.getRetweetsNum() != null) {
            sql.SET("retweets_num = #{record.retweetsNum,jdbcType=INTEGER}");
        }
        
        if (record.getReportsNum() != null) {
            sql.SET("reports_num = #{record.reportsNum,jdbcType=INTEGER}");
        }
        
        if (record.getPageViews() != null) {
            sql.SET("page_views = #{record.pageViews,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("article");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("code = #{record.code,jdbcType=VARCHAR}");
        sql.SET("author_id = #{record.authorId,jdbcType=VARCHAR}");
        sql.SET("title = #{record.title,jdbcType=VARCHAR}");
        sql.SET("category = #{record.category,jdbcType=VARCHAR}");
        sql.SET("featured_image = #{record.featuredImage,jdbcType=VARCHAR}");
        sql.SET("content_id = #{record.contentId,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=VARCHAR}");
        sql.SET("is_top = #{record.isTop,jdbcType=BIT}");
        sql.SET("likes_num = #{record.likesNum,jdbcType=INTEGER}");
        sql.SET("favorites_num = #{record.favoritesNum,jdbcType=INTEGER}");
        sql.SET("retweets_num = #{record.retweetsNum,jdbcType=INTEGER}");
        sql.SET("reports_num = #{record.reportsNum,jdbcType=INTEGER}");
        sql.SET("page_views = #{record.pageViews,jdbcType=INTEGER}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        ArticleExample example = (ArticleExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Article record) {
        SQL sql = new SQL();
        sql.UPDATE("article");
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorId() != null) {
            sql.SET("author_id = #{authorId,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getCategory() != null) {
            sql.SET("category = #{category,jdbcType=VARCHAR}");
        }
        
        if (record.getFeaturedImage() != null) {
            sql.SET("featured_image = #{featuredImage,jdbcType=VARCHAR}");
        }
        
        if (record.getContentId() != null) {
            sql.SET("content_id = #{contentId,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getIsTop() != null) {
            sql.SET("is_top = #{isTop,jdbcType=BIT}");
        }
        
        if (record.getLikesNum() != null) {
            sql.SET("likes_num = #{likesNum,jdbcType=INTEGER}");
        }
        
        if (record.getFavoritesNum() != null) {
            sql.SET("favorites_num = #{favoritesNum,jdbcType=INTEGER}");
        }
        
        if (record.getRetweetsNum() != null) {
            sql.SET("retweets_num = #{retweetsNum,jdbcType=INTEGER}");
        }
        
        if (record.getReportsNum() != null) {
            sql.SET("reports_num = #{reportsNum,jdbcType=INTEGER}");
        }
        
        if (record.getPageViews() != null) {
            sql.SET("page_views = #{pageViews,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, ArticleExample example, boolean includeExamplePhrase) {
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