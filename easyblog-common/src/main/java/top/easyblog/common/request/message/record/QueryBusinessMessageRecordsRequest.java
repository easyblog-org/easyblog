package top.easyblog.common.request.message.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-11 20:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryBusinessMessageRecordsRequest {
    private List<Long> ids;
    private String businessModule;
    private String businessEvent;
    private List<Byte> status;
    private Boolean deleted;
    @Builder.Default
    private Integer limit = 10;
    @Builder.Default
    private Integer offset = 0;
}
