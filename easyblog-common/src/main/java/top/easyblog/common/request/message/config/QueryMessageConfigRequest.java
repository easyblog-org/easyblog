package top.easyblog.common.request.message.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-05 13:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMessageConfigRequest {
    private String code;
}
