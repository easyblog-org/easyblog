package top.easyblog.common.request.message.config;

import lombok.*;
import top.easyblog.titan.nestor.enums.TemplateValueConfigType;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-04 19:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UpdateMessageConfigRequest extends BaseRequest {

    @NotBlank(message = "Required param 'name' is not present")
    private String name;

    private Boolean deleted;

    private UpdateTemplateValueConfigRequest templateValueConfig;

    @Override
    public boolean validate() {
        if(Objects.nonNull(templateValueConfig) && Objects.equals(TemplateValueConfigType.codeOf(templateValueConfig.getType()), TemplateValueConfigType.INTERFACE_DIRECT_VALUE) ||
                Objects.equals(TemplateValueConfigType.codeOf(templateValueConfig.getType()), TemplateValueConfigType.INTERFACE_JSON_VALUE)){
            assertNotEmpty(templateValueConfig.getUrl(), "Required param 'templateValueConfig.url' is not present");
        }
        return super.validate();
    }
}
