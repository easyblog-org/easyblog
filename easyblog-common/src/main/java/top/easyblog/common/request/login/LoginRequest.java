package top.easyblog.common.request.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.request.BaseRequest;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: frank.huang
 * @date: 2021-11-01 20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements BaseRequest {
    /**
     * 登录账户类型
     */
    @NotNull(message = "Required parameter `identifier_type` is not present")
    private Integer identifierType;

    /**
     * 登录账号
     */
    @NotBlank(message = "Required parameter `identifier` is not present")
    private String identifier;

    /**
     * 账户密码 或  token
     */
    private String credential;

    /**
     * 登录时关于用户的其他详细信息
     */
    private CreateLoginLogRequest extra;


    public boolean validate() {
        return IdentifierType.subCodeOf(identifierType) != null;
    }
}
