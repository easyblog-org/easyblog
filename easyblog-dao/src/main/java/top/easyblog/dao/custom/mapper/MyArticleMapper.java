package top.easyblog.dao.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.request.article.QueryArticlesRequest;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:25
 */
@Mapper
public interface MyArticleMapper {

    /**
     * 查询文章数量
     *
     * @param request
     * @return
     */
    long countByRequest(@Param("request") QueryArticlesRequest request);

    /**
     * 查询文章列表
     *
     * @param request
     * @return
     */
    List<ArticleBean> selectListByRequest(@Param("request") QueryArticlesRequest request);
}
