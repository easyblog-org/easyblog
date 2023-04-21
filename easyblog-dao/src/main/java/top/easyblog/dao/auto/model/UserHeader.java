package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class UserHeader {
    private Long id;

    private String code;

    private String headerImgUrl;

    private String userCode;

    private Byte status;

    private Date createTime;

    private Date updateTime;
}