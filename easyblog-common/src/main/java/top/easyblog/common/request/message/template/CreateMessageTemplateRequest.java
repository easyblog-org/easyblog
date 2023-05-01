package top.easyblog.common.request.message.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.enums.MessageShieldType;
import top.easyblog.common.enums.MessageTemplateType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.BaseRequest;
import top.easyblog.common.response.EasyResultCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-11 14:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageTemplateRequest implements BaseRequest {

    @NotBlank(message = "Required param 'name' is not present.")
    private String name;

    private String expectPushTime;

    @NotNull(message = "Required param 'msg_type' is not present.")
    private Byte msgType;

    @NotNull(message = "Required param 'shield_type' is not present.")
    private Byte shieldType;

    @NotBlank(message = "Required param 'msg_content' is not present.")
    private String msgContent;

    @Override
    public boolean validate() {
        MessageTemplateType messageTemplateType = MessageTemplateType.codeOf(msgType);
        if(Objects.isNull(messageTemplateType)){
            throw new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_TYPE);
        }

        MessageShieldType messageShieldType = MessageShieldType.codeOf(shieldType);
        if(Objects.isNull(messageShieldType)){
            throw new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_SHIELD_TYPE);
        }

        return true;
    }
}
