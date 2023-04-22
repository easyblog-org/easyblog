package top.easyblog.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2022-02-10 22:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileAreBean {
    private String code;

    private String continentCode;

    private String crownCode;

    private String countryCode;

    private String areaName;

    private Date createTime;

    private Date updateTime;
}
