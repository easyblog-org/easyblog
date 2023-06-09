package top.easyblog.common.request.loginlog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/02/11 17:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLoginLogRequest {

    private String code;

    private String token;

    private Integer status;

    private String ip;

    private String device;

    private String operationSystem;

    private String location;

    private Date updateTime;
}
