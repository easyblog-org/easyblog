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
    private String sendChannel;
    private Long expectPushTime;
}
