package top.easyblog.common.request.message.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-02-11 14:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageTemplateRequest {

    @NotBlank(message = "Required param 'name' is not present.")
    private String name;

    private Byte msgStatus;

    private String expectPushTime;

    private Byte idType;

    private Byte sendChannel;

    private Byte msgType;

    private Byte shieldType;

    private String msgContent;

    private Boolean deleted;
}
