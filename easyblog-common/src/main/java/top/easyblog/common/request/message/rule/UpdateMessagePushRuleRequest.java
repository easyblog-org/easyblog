package top.easyblog.common.request.message.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.request.message.config.UpdateMessageConfigRequest;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-05-05 20:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessagePushRuleRequest {

    @NotBlank(message = "Required param 'business_module' is required.")
    private String businessModule;

    @NotBlank(message = "Required param 'business_event' is required.")
    private String businessEvent;

    private String templateCode;

    private String msgGroup;

    @Min(value = 0, message = "Param 'priority'  must bigger than 0")
    private Integer priority;

    private Byte channel;

    private List<UpdateMessageConfigRequest> configs;
}
