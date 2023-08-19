package top.easyblog.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-08-19 13:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BukaSmsSendRequest {
    private String appId;
    private String numbers;
    private String content;
}
