package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-07-01 17:14
 */
@Data
public class H5ArticleBean {
    /**
     * 轮播图文章
     */
    private List<ArticleBean> swiperArticleList;
    /**
     * 轮播图侧边文章
     */
    private List<ArticleBean> swiperArticleSideList;
    /**
     * 最新文章
     */
    private List<ArticleBean> newestArticleList;


    @Data
    public static class ArticleBean {
        private Long id;

        private String code;

        private ArticleAuthor author;

        private String title;

        private List<String> categories;

        private String featuredImage;

        private String status;

        private Boolean isTop;

        private String content;

        private String category;

        private Integer likesNum;

        private Integer favoritesNum;

        private Integer retweetsNum;

        private Integer reportsNum;

        private Integer pageViews;

        private Long createTime;

        private Long updateTime;
    }

    @Data
    public static class ArticleAuthor {
        /**
         * 用户id
         */
        private Long id;
        /**
         * 用户Code
         */
        private String code;
        /**
         * 用户昵称
         */
        private String nickName;
        /**
         *
         */
        private String headerImgUrl;
    }
}
