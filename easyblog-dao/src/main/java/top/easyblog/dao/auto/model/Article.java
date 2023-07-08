package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class Article {
    private Long id;

    private String authorId;

    private String title;

    private String category;

    private String featuredImage;

    private String status;

    private Boolean isTop;

    private String contentId;

    private Date createTime;

    private Date updateTime;

    private String code;
}