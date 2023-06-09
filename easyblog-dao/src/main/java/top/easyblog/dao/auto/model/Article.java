package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class Article {
    private Long id;

    private String code;

    private String authorId;

    private String title;

    private String category;

    private String featuredImage;

    private String contentId;

    private String status;

    private Boolean isTop;

    private Integer likesNum;

    private Integer favoritesNum;

    private Integer retweetsNum;

    private Integer reportsNum;

    private Integer pageViews;

    private Date updateTime;

    private Date createTime;
}