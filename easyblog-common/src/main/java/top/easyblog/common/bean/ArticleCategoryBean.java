package top.easyblog.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2023-06-25 20:47
 */
@Data
public class ArticleCategoryBean {
    private Long id;

    private Long pid;

    private String name;

    private Date createTime;

    private Date updateTime;
}
