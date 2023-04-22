package top.easyblog.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * System unified response code
 *
 * @author frank.huang
 * @since 2021/8/21 22:13
 */
@Getter
@AllArgsConstructor
public enum EasyResultCode {
    //sever internal
    SUCCESS,
    FAIL,
    INVALID_PARAMS,
    NOT_FOUND,
    INTERNAL_ERROR,
    DATA_ACCESS_FAIL,
    PARAMETER_VALIDATE_FAILED,

    AUTH_TOKEN_NOT_FOUND,

    SIGN_FAIL,
    SIGN_ERROR,
    SIGN_NOT_FOUND,
    SING_HAS_EXPIRE,

    REMOTE_INVOKE_FAIL,
    // 数据库操作实体对象不允许为null
    DB_OPERATE_RECORD_NOT_ALLOW_NULL,
    // 非法的消息参数配置类型
    ILLEGAL_CONFIG_TYPE,
    //非法的消息参数模板取值配置类型
    ILLEGAL_TEMPLATE_VALUE_TYPE,
    // 非法消息发送发送渠道
    ILLEGAL_MESSAGE_SEND_CHANNEL,
    // 非法的消息参数id
    ILLEGAL_CONFIG_IDS,
    // 非法的消息模板code
    ILLEGAL_TEMPLATE_CODE,

    // 消息参数配置未找到
    MESSAGE_CONFIG_NOT_FOUND,
    // 消息规则未找到
    MESSAGE_CONFIG_RULE_NOT_FOUND,
    // 消息发送记录不存在
    SEND_RECORD_NOT_FOUND,
    // 发送消息失败
    SEND_MESSAGE_FAILED,
    // 不合法的消息
    ILLEGAL_MESSAGE_RECORD,
    // 模板未找到
    TEMPLATE_NOT_FOUND,

    //账号已存在
    USER_ACCOUNT_EXISTS,
    //账号不存在
    ACCOUNT_NOT_FOUND,
    //登录日志不存在
    LOGIN_LOG_NOT_FOUND,

    ;


    public String getCode() {
        return this.name().toLowerCase();
    }
}
