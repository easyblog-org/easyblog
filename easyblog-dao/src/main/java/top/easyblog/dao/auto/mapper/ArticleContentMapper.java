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
import top.easyblog.dao.auto.mapper.provider.ArticleContentSqlProvider;
import top.easyblog.dao.auto.model.ArticleContent;
import top.easyblog.dao.auto.model.example.ArticleContentExample;

@Mapper
@Repository
public interface ArticleContentMapper {
    @SelectProvider(type= ArticleContentSqlProvider.class, method="countByExample")
    long countByExample(ArticleContentExample example);

    @DeleteProvider(type=ArticleContentSqlProvider.class, method="deleteByExample")
    int deleteByExample(ArticleContentExample example);

    @Delete({
        "delete from article_content",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into article_content (content)",
        "values (#{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ArticleContent record);

    @InsertProvider(type=ArticleContentSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(ArticleContent record);

    @SelectProvider(type=ArticleContentSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ArticleContent> selectByExampleWithBLOBs(ArticleContentExample example);

    @SelectProvider(type=ArticleContentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<ArticleContent> selectByExample(ArticleContentExample example);

    @Select({
        "select",
        "id, content",
        "from article_content",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    ArticleContent selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ArticleContentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ArticleContent record, @Param("example") ArticleContentExample example);

    @UpdateProvider(type=ArticleContentSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") ArticleContent record, @Param("example") ArticleContentExample example);

    @UpdateProvider(type=ArticleContentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ArticleContent record, @Param("example") ArticleContentExample example);

    @UpdateProvider(type=ArticleContentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ArticleContent record);

    @Update({
        "update article_content",
        "set content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(ArticleContent record);
}