package top.easyblog.common.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-25 14:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserAccountRequest {
    @NotBlank(message = "Required param 'nick_name' is not present.")
    private String nickName;

    private Integer active;

    @NotEmpty(message = "Required param 'roles' is not present.")
    private List<String> roles;

    @NotNull(message = "Required param 'identity_type' is not present.")
    private Integer identityType;

    @NotBlank(message = "Required param 'email' is not present.")
    private String email;

    @NotBlank(message = "Required param 'password' is not present.")
    private String password;
}
