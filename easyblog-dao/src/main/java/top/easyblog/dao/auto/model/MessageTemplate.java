package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class MessageTemplate {
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

    private Date createTime;

    private Date updateTime;
}