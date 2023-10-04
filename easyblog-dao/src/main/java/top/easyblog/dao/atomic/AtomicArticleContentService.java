package top.easyblog.dao.atomic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.easyblog.dao.mongo.model.ArticleContent;
import top.easyblog.support.util.JsonUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2023-06-24 15:17
 */
@Service
@Slf4j
public class AtomicArticleContentService {


    @Autowired
    private MongoTemplate mongoTemplate;

    public String insertOne(ArticleContent articleContent) {
        articleContent.setCreateTime(new Date());
        articleContent.setUpdateTime(new Date());
        mongoTemplate.insert(articleContent);
        log.info("[MongoDB]Insert new article content successfully!Details==>{}", JsonUtils.toJSONString(articleContent));
        return articleContent.getContentId();
    }

    public String updateByPrimaryKeySelective(ArticleContent articleContent) {
        if (StringUtils.isBlank(articleContent.getContentId())) {
            log.info("Content is empty");
            return "";
        }
        ;
        //通过query根据content_id查询出对应对象，通过update对象进行修改
        Query query = new Query(Criteria.where("content_id").is(articleContent.getContentId()));
        Update update = new Update();
        if (StringUtils.isNotBlank(articleContent.getContent())) {
            update.set("content", articleContent.getContent());
        }
        update.set("update_time", new Date());
        mongoTemplate.updateFirst(query, update, ArticleContent.class);
        log.info("[MongoDB]Update article content successfully!Details==>{}", JsonUtils.toJSONString(articleContent));
        return articleContent.getContentId();
    }

    public List<ArticleContent> queryListByIds(List<String> content_id) {
        if (CollectionUtils.isEmpty(content_id)) {
            return Collections.emptyList();
        }
        Query query = new Query(Criteria.where("content_id").in(content_id));
        log.info("[MongoDB]MQL:{}", query);
        return mongoTemplate.find(query, ArticleContent.class);
    }

}
