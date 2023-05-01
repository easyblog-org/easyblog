package top.easyblog.common.request.message.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageTemplateRequest {
    private String msgContent;
    private Boolean deleted;
}
