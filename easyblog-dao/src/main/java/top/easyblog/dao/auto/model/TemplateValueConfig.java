package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class TemplateValueConfig {
    private Long id;

    private Byte type;

    private String expression;

    private String url;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}