package top.easyblog.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.easyblog.common.enums.ArticleEventEnum;
import top.easyblog.common.request.article.CreateArticleEventRequest;
import top.easyblog.common.request.article.QueryArticleEventRequest;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicArticleEventService;
import top.easyblog.dao.auto.model.ArticleEvent;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-07-23 12:12
 */
@Service
public class ArticleEventService {

    @Autowired
    private AtomicArticleEventService atomicArticleEventService;

    @Autowired
    private BeanMapper beanMapper;


    public Long saveArticleEvent(CreateArticleEventRequest request) {
        ArticleEventEnum articleEventEnum = ArticleEventEnum.nameOf(request.getEvent());
        Assert.notNull(articleEventEnum, "Illegal article event:" + request.getEvent());
        ArticleEvent event = beanMapper.buildArticleEvent(request);

        atomicArticleEventService.insertOne(event);
        return event.getId();
    }

    public Long countByRequest(QueryArticleEventRequest request) {
        return atomicArticleEventService.countByRequest(request);
    }

    public List<ArticleEvent> list(QueryArticleEventRequest request) {
        return atomicArticleEventService.queryListByRequest(request);
    }

    public void deleteByIds(List<Long> ids) {
        atomicArticleEventService.deleteByIds(ids);
    }

}
