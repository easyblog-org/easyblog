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
import top.easyblog.dao.auto.mapper.provider.ArticleCategorySqlProvider;
import top.easyblog.dao.auto.model.ArticleCategory;
import top.easyblog.dao.auto.model.example.ArticleCategoryExample;

@Mapper
@Repository
public interface ArticleCategoryMapper {
    @SelectProvider(type= ArticleCategorySqlProvider.class, method="countByExample")
    long countByExample(ArticleCategoryExample example);

    @DeleteProvider(type=ArticleCategorySqlProvider.class, method="deleteByExample")
    int deleteByExample(ArticleCategoryExample example);

    @Delete({
        "delete from article_category",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into article_category (pid, name, ",
        "create_time, update_time)",
        "values (#{pid,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(ArticleCategory record);

    @InsertProvider(type=ArticleCategorySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(ArticleCategory record);

    @SelectProvider(type=ArticleCategorySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="pid", property="pid", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ArticleCategory> selectByExample(ArticleCategoryExample example);

    @Select({
        "select",
        "id, pid, name, create_time, update_time",
        "from article_category",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="pid", property="pid", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    ArticleCategory selectByPrimaryKey(Long id);

    @UpdateProvider(type=ArticleCategorySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ArticleCategory record, @Param("example") ArticleCategoryExample example);

    @UpdateProvider(type=ArticleCategorySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ArticleCategory record, @Param("example") ArticleCategoryExample example);

    @UpdateProvider(type=ArticleCategorySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ArticleCategory record);

    @Update({
        "update article_category",
        "set pid = #{pid,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ArticleCategory record);
}