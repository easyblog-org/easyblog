package top.easyblog.common.request.message.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageConfigRuleRequest {
    @NotBlank(message = "Required param 'business_module' is not present.")
    private String businessModule;

    @NotBlank(message = "Required param 'business_event' is not present.")
    private String businessEvent;

    @NotBlank(message = "Required param 'template_code' is not present.")
    private String templateCode;

    private String msgGroup;

    @Builder.Default
    @Min(value = 1, message = "Param 'priority'  must bigger than 1")
    private Integer priority = 1;

    @NotNull(message = "Required param 'channel' is not present.")
    private Byte channel;

    private String configIds;
}
