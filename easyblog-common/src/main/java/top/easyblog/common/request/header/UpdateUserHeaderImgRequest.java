package top.easyblog.common.request.header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/11 15:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserHeaderImgRequest {
    private String code;

    private String headerImgUrl;

    private String userCode;

    private Integer status;
}
