package top.easyblog.dao.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2023-07-08 15:31
 */
@Data
@Document(collection = "article_content")
public class ArticleContent {

    @Id
    @Field("_id")
    private String id;

    @Field("content_id")
    private String contentId;

    //文章内容
    private String content;

    @Field("create_time")
    private Date createTime;

    @Field("update_time")
    private Date updateTime;

}
