package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleEventBean {
    private Long id;

    private String articleCode;

    private String userCode;

    private String event;

    private String operator;

    private Date createTime;

    private Date updateTime;
}