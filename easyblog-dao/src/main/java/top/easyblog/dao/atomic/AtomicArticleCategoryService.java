package top.easyblog.dao.atomic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.article.QueryArticleCategoryListRequest;
import top.easyblog.dao.auto.mapper.ArticleCategoryMapper;
import top.easyblog.dao.auto.model.ArticleCategory;
import top.easyblog.dao.auto.model.example.ArticleCategoryExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:17
 */
@Slf4j
@Service
public class AtomicArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper mapper;

    public void insertOne(ArticleCategory record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB]Insert new article category.Details==>{}", JsonUtils.toJSONString(record));
    }


    public void updateByPrimaryKeySelective(ArticleCategory record) {
        record.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(record);
        log.info("[DB]Update article category.Details==>{}", JsonUtils.toJSONString(record));
    }

    public long countByRequest(QueryArticleCategoryListRequest request) {
        return mapper.countByExample(generateExamples(request));
    }

    public List<ArticleCategory> queryListByRequest(QueryArticleCategoryListRequest request) {
        ArticleCategoryExample example = generateExamples(request);
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }

        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }

        return mapper.selectByExample(example);
    }

    private ArticleCategoryExample generateExamples(QueryArticleCategoryListRequest request) {
        ArticleCategoryExample example = new ArticleCategoryExample();
        ArticleCategoryExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }
        if (CollectionUtils.isNotEmpty(request.getPids())) {
            criteria.andPidIn(request.getPids());
        }
        if (CollectionUtils.isNotEmpty(request.getNames())) {
            criteria.andNameIn(request.getNames());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }
        return example;
    }

}
