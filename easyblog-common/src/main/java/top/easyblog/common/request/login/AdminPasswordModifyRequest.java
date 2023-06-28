package top.easyblog.common.request.login;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-03-05 19:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPasswordModifyRequest {
    @NotBlank(message = "Required param 'identify' is not present.")
    private String identify;

    @NotBlank(message = "Required param 'password' is not present.")
    private String password;

    @NotBlank(message = "Required param 'config_password' is not present.")
    private String configPassword;
}
