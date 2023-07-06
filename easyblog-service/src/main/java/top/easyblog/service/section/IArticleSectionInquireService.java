package top.easyblog.service.section;

import top.easyblog.common.bean.ArticleBean;
import top.easyblog.support.context.ArticleSectionContext;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-26 21:32
 */
public interface IArticleSectionInquireService {
        /**
         * 执行选项信息查询
         *
         * @param section               选项名称
         * @param ctx                   上下文
         * @param queryParams           查询参数
         * @param queryWhenSectionEmpty 是否在选项名称为空时继续执行查询
         * @return
         */
        void execute(String section, ArticleSectionContext ctx, List<ArticleBean> queryParams,
                        boolean queryWhenSectionEmpty);

        /**
         * 执行选项信息查询
         *
         * @param section     选项名称
         * @param ctx         上下文
         * @param queryParams 查询参数
         * @return
         */
        default void execute(String section, ArticleSectionContext ctx, List<ArticleBean> queryParams) {
                execute(section, ctx, queryParams, false);
        }

        /**
         * 执行选项信息查询,当选项参数为空时将继续执行查询
         *
         * @param ctx         上下文
         * @param queryParams 查询参数
         * @return
         */
        default void execute(ArticleSectionContext ctx, List<ArticleBean> queryParams) {
                execute(null, ctx, queryParams, true);
        }
}
