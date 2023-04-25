package top.easyblog.common.bean;

import lombok.Data;

@Data
public class MessageTemplateBean {
    private Long id;

    private String templateCode;

    private String name;

    private Byte msgStatus;

    private String expectPushTime;

    private Byte idType;

    private Byte sendChannel;

    private Byte msgType;

    private Byte shieldType;

    private String msgContent;

    private Boolean deleted;

    private Long createTime;

    private Long updateTime;
}
