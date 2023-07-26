package top.easyblog.dao.atomic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.article.QueryArticleEventRequest;
import top.easyblog.dao.auto.mapper.ArticleEventMapper;
import top.easyblog.dao.auto.model.ArticleEvent;
import top.easyblog.dao.auto.model.example.ArticleEventExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-07-23 11:43
 */
@Slf4j
@Service
public class AtomicArticleEventService {

    @Autowired
    private ArticleEventMapper mapper;


    public Long insertOne(ArticleEvent record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB]Insert new article event success!Details==>{}", JsonUtils.toJSONString(record));
        return record.getId();
    }

    public List<ArticleEvent> queryListByRequest(QueryArticleEventRequest request) {
        ArticleEventExample example = generateExamples(request);
        return mapper.selectByExample(example);
    }

    public long countByRequest(QueryArticleEventRequest request) {
        ArticleEventExample example = generateExamples(request);
        return mapper.countByExample(example);
    }

    private ArticleEventExample generateExamples(QueryArticleEventRequest request) {
        ArticleEventExample example = new ArticleEventExample();
        ArticleEventExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getEvents())) {
            criteria.andEventIn(request.getEvents());
        }
        if (CollectionUtils.isNotEmpty(request.getArticleCodes())) {
            criteria.andArticleCodeIn(request.getArticleCodes());
        }
        if (CollectionUtils.isNotEmpty(request.getUserCodes())) {
            criteria.andArticleCodeIn(request.getArticleCodes());
        }
        if (CollectionUtils.isNotEmpty(request.getOperators())) {
            criteria.andOperatorIn(request.getOperators());
        }
        return example;
    }

    public void deleteByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.info("[DB]Empty article_event delete id list,ignore.");
            return;
        }

        ArticleEventExample example = new ArticleEventExample();
        ArticleEventExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        mapper.deleteByExample(example);
        log.info("[DB]Delete article_even by ids {}", ids);
    }
}
