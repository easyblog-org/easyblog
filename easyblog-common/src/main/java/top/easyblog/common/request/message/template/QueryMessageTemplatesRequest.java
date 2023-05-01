package top.easyblog.common.request.message.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMessageTemplatesRequest {
    private List<String> templateCodes;
    private String name;
    private List<Short> status;
    private List<Byte> msgType;
    private List<Byte> shieldType;
    @Builder.Default
    private Integer offset = 0;
    @Builder.Default
    private Integer limit = 10;
}
