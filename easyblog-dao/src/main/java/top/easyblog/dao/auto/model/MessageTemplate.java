package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class MessageTemplate {
    private Long id;

    private String templateCode;

    private String name;

    private Short status;

    private String expectPushTime;

    private Byte msgType;

    private Byte shieldType;

    private String msgContent;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}