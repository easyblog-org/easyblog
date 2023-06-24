package top.easyblog.dao.atomic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.dao.auto.mapper.ArticleMapper;
import top.easyblog.dao.auto.model.Article;
import top.easyblog.dao.custom.mapper.MyArticleMapper;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:13
 */
@Slf4j
@Service
public class AtomicArticleService {

    @Autowired
    private ArticleMapper mapper;

    @Autowired
    private MyArticleMapper myArticleMapper;

    public Long insertOne(Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        long lastID = mapper.insertSelective(article);
        log.info("[DB] Insert new article [lastID={}] successfully!Details==>{}", lastID, JsonUtils.toJSONString(article));
        return lastID;
    }


    public void updateByPrimaryKeySelective(Article article) {
        article.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(article);
        log.info("[DB]Update article [id={}] successfully!Details==>{}", article.getId(), JsonUtils.toJSONString(article));
    }


    public long countByRequest(QueryArticlesRequest request) {
        return myArticleMapper.countByRequest(request);
    }


    public List<ArticleBean> queryListByRequest(QueryArticlesRequest request) {
        return myArticleMapper.selectListByRequest(request);
    }


}
