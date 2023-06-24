package top.easyblog.dao.atomic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.dao.auto.mapper.ArticleContentMapper;
import top.easyblog.dao.auto.model.ArticleContent;
import top.easyblog.support.util.JsonUtils;

/**
 * @author: frank.huang
 * @date: 2023-06-24 15:17
 */
@Service
@Slf4j
public class AtomicArticleContentService {


    @Autowired
    private ArticleContentMapper articleContentMapper;

    public Long insertOne(ArticleContent articleContent) {
        articleContentMapper.insertSelective(articleContent);
        log.info("[DB]Insert new article content successfully!Details==>{}", JsonUtils.toJSONString(articleContent));
        return articleContent.getId().longValue();
    }

    public Long updateByPrimaryKeySelective(ArticleContent articleContent) {
        articleContentMapper.updateByPrimaryKeySelective(articleContent);
        log.info("[DB]Update article content successfully!Details==>{}", JsonUtils.toJSONString(articleContent));
        return articleContent.getId().longValue();
    }

}
