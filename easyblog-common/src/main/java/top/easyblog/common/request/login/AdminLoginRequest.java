package top.easyblog.common.request.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-03-03 20:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginRequest {
    /**
     * 邮箱号
     */
    @NotBlank(message = "Required param 'email' is not present.")
    private String email;
    /**
     * 密码
     */
    @NotBlank(message = "Required param 'password' is not present.")
    private String password;
    /**
     * 登录设备ip
     */
    private String ip;
    /**
     * 登录设备名称
     */
    private String device;
    /**
     * 登录设备操作系统
     */
    private String operationSystem;
    /**
     * 登录物理定位
     */
    private String location;
}
