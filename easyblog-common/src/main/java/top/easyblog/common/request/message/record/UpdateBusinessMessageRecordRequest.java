package top.easyblog.common.request.message.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-12 13:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBusinessMessageRecordRequest {
    private String businessId;
    private String businessModule;
    private String businessEvent;
    private String businessMessage;
    private Byte status;
    private Integer retryTimes;
    private String failReason;
    private Boolean deleted;
}
