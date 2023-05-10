package top.easyblog.common.request.message.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.InputStream;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-12 13:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusinessMessageRecordRequest {
    private String businessId;
    private String businessModule;
    private String businessEvent;
    private String businessMessage;
}
