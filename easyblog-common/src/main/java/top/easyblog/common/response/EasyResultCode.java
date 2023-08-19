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
    // sever internal
    SUCCESS,
    FAIL,
    INVALID_PARAMS,
    NOT_FOUND,
    INTERNAL_ERROR,
    DATA_ACCESS_FAIL,
    PARAMETER_VALIDATE_FAILED,
    LOGIN_FAILED,
    PASSWORD_VALID_FAILED,
    REQUEST_GITEE_ACCESS_TOKEN_FAILED,
    REQUEST_GITEE_USER_INFO_FAILED,
    REQUEST_GITHUB_ACCESS_TOKEN_FAILED,
    REQUEST_GITHUB_USER_INFO_FAILED,

    SIGN_FAIL,
    SIGN_ERROR,
    SIGN_NOT_FOUND,
    SING_HAS_EXPIRE,

    // 非法参数
    ILLEGAL_PARAM,
    // 远程调用失败
    REMOTE_INVOKE_FAIL,
    // 数据库操作实体对象不允许为null
    DB_OPERATE_RECORD_NOT_ALLOW_NULL,
    // 非法的消息参数配置类型
    ILLEGAL_CONFIG_TYPE,
    // 非法的消息参数模板取值配置类型
    ILLEGAL_TEMPLATE_VALUE_TYPE,
    // 非法参数配置
    ILLEGAL_TEMPLATE_VALUE_CONFIG,
    // 非法消息发送发送渠道
    ILLEGAL_MESSAGE_SEND_CHANNEL,
    // 非法的消息参数id
    ILLEGAL_CONFIG_IDS,
    // 非法的消息模板code
    ILLEGAL_TEMPLATE_CODE,
    // 不正确的登录策略
    INCORRECT_LOGIN_POLICY,
    // 不正确的Ouath2平台
    INCORRECT_OAUTH2_POLICY,
    // 非法认证策略
    INCORRECT_OAUTH_POLICY,
    // 不正确的业务事件或模块
    INCORRECT_BUSINESS_EVENT_OR_MODULE,
    // 必须得请求参数不存在
    REQUIRED_REQUEST_PARAM_NOT_EXISTS,
    // 非法账号类型
    INVALID_IDENTITY_TYPE,
    // 不正确的模板Code
    INCORRECT_TEMPLATE_CODE,
    // 不正确的消息配置
    INCORRECT_MESSAGE_CONFIGS,
    // 非法消息类型
    ILLEGAL_MESSAGE_TYPE,
    // 非法夜间屏蔽类型
    ILLEGAL_MESSAGE_SHIELD_TYPE,

    // 发送消息失败
    SEND_MESSAGE_FAILED,
    // 不合法的消息
    ILLEGAL_MESSAGE_RECORD,
    // 不正确或已过期的验证码
    INCORRECT_OR_EXPIRE_CAPTCHA,
    // 消息不能为空
    MESSAGE_RECORD_CANNOT_NULL,
    //非法消息推送操作
    ILLEGAL_MESSAGE_RECORD_OPERATION,

    // 账号已存在
    USER_ACCOUNT_EXISTS,
    // 角色已存在
    ROLE_EXISTS,
    // 手机区域码已存在
    PHONE_AREA_CODE_ALREADY_EXISTS,
    // 手机站账号已经存在
    PHONE_ACCOUNT_ALREADY_EXISTS,
    // 邮箱账号存在
    EMAIL_ACCOUNT_EXISTS,
    // 模板已存在
    TEMPLATE_ALREADY_EXISTS,
    //消息推送规则已经存在
    MESSAGE_CONFIG_RULE_EXISTS,


    // 账号不存在
    ACCOUNT_NOT_FOUND,
    // 登录日志不存在
    LOGIN_LOG_NOT_FOUND,
    // 手机区域码不存在
    MOBILE_AREA_NOT_FOUND,
    // 模板未找到
    TEMPLATE_NOT_FOUND,
    // 消息参数配置未找到
    MESSAGE_CONFIG_NOT_FOUND,
    // 消息规则未找到
    MESSAGE_CONFIG_RULE_NOT_FOUND,
    // 消息发送记录不存在
    SEND_RECORD_NOT_FOUND,
    // 角色不存在
    ROLE_NOT_FOUND,
    // 用户不存在
    USER_NOT_FOUND,
    // 认证Token不存在
    AUTH_TOKEN_NOT_FOUND,
    // 认证过期
    AUTH_EXPIRED,
    //发送记录未找到
    MESSAGE_RECORD_NOT_FOUND,
    // 文章已经被删除
    ARTICLE_ALREADY_DELETED,

    // 无法识别的大洲编码
    UNKNOWN_CONTINENT_CODE,
    // 账号未激活
    ACCOUNT_IS_PRE_ACTIVE,
    // 账号被删除
    ACCOUNT_IS_DELETE,
    // 账号被冻结
    ACCOUNT_IS_FREEZE,
    // 账号不是邮箱
    IDENTIFIER_NOT_EMAIL,
    // 账号不是手机
    IDENTIFIER_NOT_PHONE,
    // 无删除权限
    DELETE_OPERATION_NOT_PERMISSION,
    // 密码不一致
    PASSWORD_NOT_EQUAL,
    // 统计指标不存在
    STATISTIC_INDEX_NOT_FOUND,
    // 短信推送失败
    SMS_PUSH_FAILED,

    CREATE_USER_FAILED,
    INVALID_CONTINENT_TYPE,
    MESSAGE_PARAM_CONFIG_REQUIRED,
    MESSAGE_PARAM_CONFIG_RECEIVER_REQUIRED,
    MESSAGE_TEMPLATE_NOT_FOUND,
    MESSAGE_TEMPLATE_STATUS_ILLEGAL,
    PASSWORD_NOT_CHANGE,
    NO_ACCESS_PERMISSION,
    INCORRECT_CAPTCHA_POLICY,
    CAPTCHA_EXPIRED_OR_NOT_EXISTS,
    ;

    public String getCode() {
        return this.name().toLowerCase();
    }
}
