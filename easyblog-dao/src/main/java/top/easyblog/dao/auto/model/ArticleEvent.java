package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class ArticleEvent {
    private Long id;

    private String articleCode;

    private String userCode;

    private String event;

    private String operator;

    private Date createTime;

    private Date updateTime;
}