package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class MessageConfig {
    private Long id;

    private String code;

    private String type;

    private String name;

    private Long templateValueConfigId;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}