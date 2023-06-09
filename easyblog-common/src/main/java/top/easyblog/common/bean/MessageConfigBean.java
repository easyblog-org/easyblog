package top.easyblog.common.bean;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:58
 */
@Data
public class MessageConfigBean {
    private String code;

    private String type;

    private String name;

    private TemplateValueConfigBean templateValueConfig;

    private Boolean deleted;

    private Long createTime;

    private Long updateTime;
}
