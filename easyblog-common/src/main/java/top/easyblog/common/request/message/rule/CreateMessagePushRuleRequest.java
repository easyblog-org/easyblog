package top.easyblog.common.request.message.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.request.message.config.CreateMessageConfigRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-05-04 21:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessagePushRuleRequest {

    @NotNull(message = "Required parameter 'message_rule_config' is not present.")
    private CreateMessageConfigRuleRequest messageRuleConfig;

    @NotEmpty(message = "Required parameter 'message_parameter_configs' is not present.")
    private List<CreateMessageConfigRequest> messageParameterConfigs;
}
