package top.easyblog.dao.auto.model;

import java.util.Date;
import lombok.Data;

@Data
public class MobileAreaCode {
    private Long id;

    private String code;

    private String continentCode;

    private String crownCode;

    private String countryCode;

    private String areaCode;

    private String areaName;

    private Date createTime;

    private Date updateTime;
}