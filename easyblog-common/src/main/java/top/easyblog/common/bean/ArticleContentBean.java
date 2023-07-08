package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2023-07-08 16:05
 */
@Data
public class ArticleContentBean {

    private String contentId;

    //文章内容
    private String content;

    private Date createTime;

    private Date updateTime;
}
