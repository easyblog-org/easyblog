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
import top.easyblog.dao.auto.mapper.provider.ArticleSqlProvider;
import top.easyblog.dao.auto.model.Article;
import top.easyblog.dao.auto.model.example.ArticleExample;

@Mapper
@Repository
public interface ArticleMapper {
    @SelectProvider(type= ArticleSqlProvider.class, method="countByExample")
    long countByExample(ArticleExample example);

    @DeleteProvider(type=ArticleSqlProvider.class, method="deleteByExample")
    int deleteByExample(ArticleExample example);

    @Delete({
        "delete from article",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into article (code, author_id, ",
        "title, category, ",
        "featured_image, content_id, ",
        "status, is_top, likes_num, ",
        "favorites_num, retweets_num, ",
        "reports_num, page_views, ",
        "update_time, create_time)",
        "values (#{code,jdbcType=VARCHAR}, #{authorId,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{category,jdbcType=VARCHAR}, ",
        "#{featuredImage,jdbcType=VARCHAR}, #{contentId,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=VARCHAR}, #{isTop,jdbcType=BIT}, #{likesNum,jdbcType=INTEGER}, ",
        "#{favoritesNum,jdbcType=INTEGER}, #{retweetsNum,jdbcType=INTEGER}, ",
        "#{reportsNum,jdbcType=INTEGER}, #{pageViews,jdbcType=INTEGER}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Article record);

    @InsertProvider(type=ArticleSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Article record);

    @SelectProvider(type=ArticleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="author_id", property="authorId", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="featured_image", property="featuredImage", jdbcType=JdbcType.VARCHAR),
        @Result(column="content_id", property="contentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_top", property="isTop", jdbcType=JdbcType.BIT),
        @Result(column="likes_num", property="likesNum", jdbcType=JdbcType.INTEGER),
        @Result(column="favorites_num", property="favoritesNum", jdbcType=JdbcType.INTEGER),
        @Result(column="retweets_num", property="retweetsNum", jdbcType=JdbcType.INTEGER),
        @Result(column="reports_num", property="reportsNum", jdbcType=JdbcType.INTEGER),
        @Result(column="page_views", property="pageViews", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Article> selectByExample(ArticleExample example);

    @Select({
        "select",
        "id, code, author_id, title, category, featured_image, content_id, status, is_top, ",
        "likes_num, favorites_num, retweets_num, reports_num, page_views, update_time, ",
        "create_time",
        "from article",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="author_id", property="authorId", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="featured_image", property="featuredImage", jdbcType=JdbcType.VARCHAR),
        @Result(column="content_id", property="contentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_top", property="isTop", jdbcType=JdbcType.BIT),
        @Result(column="likes_num", property="likesNum", jdbcType=JdbcType.INTEGER),
        @Result(column="favorites_num", property="favoritesNum", jdbcType=JdbcType.INTEGER),
        @Result(column="retweets_num", property="retweetsNum", jdbcType=JdbcType.INTEGER),
        @Result(column="reports_num", property="reportsNum", jdbcType=JdbcType.INTEGER),
        @Result(column="page_views", property="pageViews", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Article selectByPrimaryKey(Long id);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Article record);

    @Update({
        "update article",
        "set code = #{code,jdbcType=VARCHAR},",
          "author_id = #{authorId,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "category = #{category,jdbcType=VARCHAR},",
          "featured_image = #{featuredImage,jdbcType=VARCHAR},",
          "content_id = #{contentId,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=VARCHAR},",
          "is_top = #{isTop,jdbcType=BIT},",
          "likes_num = #{likesNum,jdbcType=INTEGER},",
          "favorites_num = #{favoritesNum,jdbcType=INTEGER},",
          "retweets_num = #{retweetsNum,jdbcType=INTEGER},",
          "reports_num = #{reportsNum,jdbcType=INTEGER},",
          "page_views = #{pageViews,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Article record);
}