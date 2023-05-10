package top.easyblog.common.request.mobilearea;

import lombok.*;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2022-02-10 22:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMobileAreaListRequest {
    private List<String> codes;

    private String continentCode;

    private String countryCode;

    private String areaName;

    @Builder.Default
    private Integer offset = 0;

    @Builder.Default
    private Integer limit = 10;
}
