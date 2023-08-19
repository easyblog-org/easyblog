package top.easyblog.client.dto.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-08-19 13:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BukaSmsSendDTO {
    /**
     * 状态码，0成功，其他失败参见 https://www.onbuka.cn/sms-api1/#s1
     */
    private String status;
    /**
     * 失败原因说明
     */
    private String reason;
    /**
     * 提交成功的号码个数
     */
    private String success;
    /**
     * 提交失败的号码个数
     */
    private String fail;
}
