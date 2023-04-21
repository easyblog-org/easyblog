package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class PhoneAuth {
    private Long id;

    private String code;

    private String mobileAreaCodeId;

    private String phone;

    private Date createTime;

    private Date updateTime;
}