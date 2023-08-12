package top.easyblog.common.request.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: frank.huang
 * @date: 2023-08-11 22:22
 */
@Data
public class UserLoginRequest {
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
