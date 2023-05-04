package top.easyblog.common.bean;

import lombok.Data;

@Data
public class MessageTemplateBean {
    private Long id;

    private String templateCode;

    private String name;

    private Short status;

    private String expectPushTime;

    private Byte msgType;

    private Byte shieldType;

    private String msgContent;

    private Boolean deleted;

    private Long createTime;

    private Long updateTime;
}
