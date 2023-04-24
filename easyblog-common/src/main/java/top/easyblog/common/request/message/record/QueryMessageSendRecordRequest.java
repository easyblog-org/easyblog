package top.easyblog.common.request.message.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-11 20:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryMessageSendRecordRequest {
    private Long id;
}
