package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class MessageConfigRule {
    private Long id;

    private String businessModule;

    private String businessEvent;

    private String templateCode;

    private Byte channel;

    private String msgGroup;

    private Integer priority;

    private String configIds;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}