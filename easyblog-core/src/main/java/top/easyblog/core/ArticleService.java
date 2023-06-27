package top.easyblog.core;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.enums.ArticleStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.article.CreateArticleRequest;
import top.easyblog.common.request.article.QueryArticlesRequest;
import top.easyblog.common.request.article.UpdateArticleRequest;
import top.easyblog.common.request.user.QueryUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.annotation.Transaction;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicArticleContentService;
import top.easyblog.dao.atomic.AtomicArticleService;
import top.easyblog.dao.atomic.AtomicUserService;
import top.easyblog.dao.auto.model.Article;
import top.easyblog.dao.auto.model.ArticleContent;
import top.easyblog.dao.auto.model.User;
import top.easyblog.service.section.IArticleSectionInquireService;
import top.easyblog.support.context.ArticleSectionContext;
import top.easyblog.support.util.ConcurrentUtils;

import java.util.*;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:13
 */
@Slf4j
@Service
public class ArticleService {

    @Autowired
    private AtomicArticleService atomicArticleService;

    @Autowired
    private AtomicArticleContentService atomicArticleContentService;

    @Autowired
    private AtomicUserService atomicUserService;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    List<IArticleSectionInquireService> articleSectionInquireServices;

    /**
     * 保存文章
     *
     * @param request
     * @return
     */
    @Transaction
    public String saveArticle(CreateArticleRequest request) {
        checkCreateReqValid(request);
        Long contentID = refreshArticleContentIfNeed(request.getContent(), null);
        Assert.notNull(contentID, "Save article content failed.");
        Article article = beanMapper.convertArticleCreateReq2Article(request, contentID);
        atomicArticleService.insertOne(article);
        return article.getCode();
    }


    private void checkCreateReqValid(CreateArticleRequest request) {
        checkAuthorInfoValid(request);
        checkArticleStatusValid(request.getStatus());
    }


    private void checkAuthorInfoValid(CreateArticleRequest request) {
        User user = atomicUserService.queryByRequest(QueryUserRequest.builder()
                .code(request.getAuthorId()).build());
        Assert.notNull(user, " Article author info not valid.");
    }

    private void checkArticleStatusValid(String status) {
        ArticleStatus articleStatus = ArticleStatus.nameOf(status);
        Assert.notNull(articleStatus, "Article status not valid.");
    }


    /**
     * 更新文章
     *
     * @param code
     * @param request
     */
    public void updateArticle(String code, UpdateArticleRequest request) {
        ArticleBean articleBean = details(code, null);
        checkUpdateReqValid(request, articleBean);
        Assert.notNull(articleBean, "Article of code+" + code + "+ not found!");

        Long contentId = refreshArticleContentIfNeed(request.getContent(), articleBean.getContentId());
        Article article = beanMapper.convertArticleUpdateReq2Article(request, articleBean.getId(), contentId);
        atomicArticleService.updateByPrimaryKeySelective(article);
    }

    private void checkUpdateReqValid(UpdateArticleRequest request, ArticleBean articleBean) {
        if (StringUtils.isNotBlank(request.getStatus())) {
            checkArticleStatusValid(request.getStatus());
            if (articleUndoDeleteExpired(articleBean)) {
                throw new BusinessException(EasyResultCode.ARTICLE_ALREADY_DELETED);
            }
        }
    }


    private boolean articleUndoDeleteExpired(ArticleBean articleBean) {
        return Objects.nonNull(articleBean) &&
                StringUtils.equalsIgnoreCase(ArticleStatus.DELETED.name(), articleBean.getStatus());
    }


    private Long refreshArticleContentIfNeed(String content, Long contentId) {
        if (StringUtils.isBlank(content)) {
            log.info("Article content is blank,ignore!");
            return null;
        }

        ArticleContent articleContent = beanMapper.buildArticleContent(content);
        articleContent.setId(Optional.ofNullable(contentId).map(Long::intValue).orElse(null));
        return Objects.isNull(contentId) ?
                atomicArticleContentService.insertOne(articleContent) :
                atomicArticleContentService.updateByPrimaryKeySelective(articleContent);
    }


    /**
     * 查询文章详情
     *
     * @param code
     * @return
     */
    public ArticleBean details(String code, String sections) {
        List<ArticleBean> articleBeans = atomicArticleService.queryListByRequest(QueryArticlesRequest.builder()
                .codes(Collections.singletonList(code)).build());
        ArticleBean articleBean = CollectionUtils.isEmpty(articleBeans) ? null : Iterables.getFirst(articleBeans, null);
        fillSections(sections, Collections.singletonList(articleBean));
        return articleBean;
    }

    /**
     * 查询文章列表
     *
     * @param request
     * @return
     */
    public PageResponse<ArticleBean> list(QueryArticlesRequest request) {
        long hits = atomicArticleService.countByRequest(request);
        PageResponse<ArticleBean> pageResponse = PageResponse.<ArticleBean>builder()
                .total(hits)
                .limit(request.getLimit())
                .offset(request.getOffset())
                .data(Collections.emptyList()).build();
        if (Objects.equals(NumberUtils.LONG_ZERO, hits)) {
            return pageResponse;
        }

        List<ArticleBean> articleBeans = atomicArticleService.queryListByRequest(request);
        fillSections(request.getSections(), articleBeans);
        pageResponse.setData(articleBeans);
        return pageResponse;
    }

    private void fillSections(String sections, List<ArticleBean> articleBeans) {
        ArticleSectionContext ctx = queryArticleSectionInfo(sections, articleBeans);
        articleBeans.forEach(articleBean -> {
            articleBean.setCategory(Optional.ofNullable(ctx.getArticleCategoryBeanMap()).map(map -> map.get(articleBean.getCategoryId())).orElse(null));
            articleBean.setAuthor(Optional.ofNullable(ctx.getAuthorMap()).map(map -> map.get(articleBean.getAuthorId())).orElse(null));
            articleBean.setAuthorAvatar(Optional.ofNullable(ctx.getAuthorAvatarBeanMap()).map(map -> map.get(articleBean.getAuthorId())).orElse(null));
        });
    }

    private ArticleSectionContext queryArticleSectionInfo(String sections, List<ArticleBean> articleBeans) {
        ArticleSectionContext ctx = new ArticleSectionContext();
        List<Runnable> tasks = new ArrayList<>();
        articleSectionInquireServices.forEach(executor -> {
            tasks.add(() -> executor.execute(sections, ctx, articleBeans, true));
        });

        ConcurrentUtils.executeTaskInBlockModel(tasks, null);
        return ctx;
    }
}
