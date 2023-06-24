package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class ArticleCategory {
    private Long id;

    private Long pid;

    private Date createTime;

    private Date updateTime;

    private byte[] name;
}