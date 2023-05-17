package top.easyblog.common.request.message.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-02-06 19:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageConfigRuleRequest {
    @NotBlank
    private String businessModule;
    @NotBlank
    private String businessEvent;

    private String templateCode;

    private String msgGroup;

    @Min(value = 0, message = "Param 'priority'  must bigger than 0")
    private Integer priority;

    private Byte channel;

    private String configIds;
}
