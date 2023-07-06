package top.easyblog.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2022-02-10 22:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileAreaCodeMapBean {
    private String continentCode;

    private String continentName;

    private Long total;

    private List<MobileAreBean> children;
}
