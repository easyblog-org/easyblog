package top.easyblog.common.request.message.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-02-12 13:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusinessMessageRecordRequest {
    private String businessId;
    @NotBlank(message = "Required param 'business_module' is not present.")
    private String businessModule;
    @NotBlank(message = "Required param 'business_event' is not present.")
    private String businessEvent;
    @NotBlank(message = "Required param 'business_message' is not present.")
    private String businessMessage;
    @NotBlank(message = "Required param 'template_code' is not present.")
    private String templateCode;
    @Default
    private Boolean isSync = false;
    private Byte status;
}
