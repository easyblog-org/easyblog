package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-06-24 14:26
 */
@Data
public class ArticleBean {

    private Long id;

    private String code;

    private String authorId;

    /**
     * 作者信息
     */
    private UserDetailsBean author;

    /**
     * 作者信息头像
     */
    private UserAvatarBean authorAvatar;

    private String title;

    private String categoryIds;

    private List<ArticleCategoryBean> categories;

    private String featuredImage;

    private String status;

    private Boolean isTop;

    private String contentId;

    private String content;

    private Date createTime;

    private Date updateTime;

    private String category;

    private Integer likesNum;

    private Integer favoritesNum;

    private Integer retweetsNum;

    private Integer reportsNum;

    private Integer pageViews;
}
