package top.easyblog.core.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import top.easyblog.common.bean.*;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;
import top.easyblog.common.request.loginlog.UpdateLoginLogRequest;
import top.easyblog.common.request.message.config.CreateMessageConfigRequest;
import top.easyblog.common.request.message.config.CreateTemplateValueConfigRequest;
import top.easyblog.common.request.message.config.UpdateMessageConfigRequest;
import top.easyblog.common.request.message.config.UpdateTemplateValueConfigRequest;
import top.easyblog.common.request.message.rule.CreateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.UpdateMessageConfigRuleRequest;
import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.common.request.phoneauth.CreatePhoneAuthRequest;
import top.easyblog.common.request.user.CreateUserRequest;
import top.easyblog.dao.auto.model.*;
import top.easyblog.support.context.MessageConfigContext;

/**
 * @author: frank.huang
 * @date: 2023-04-22 15:53
 */
@Mapper(componentModel = "spring")
public interface BeanMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    Account convertAccountCreateReq2Account(CreateAccountRequest request);


    AccountBean convertAccount2AccountBean(Account account);

    @Mapping(target = "userCode", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", source = "accountId")
    Account convertAccountUpdateReq2Account(Long accountId, UpdateAccountRequest request);


    @Mapping(target = "ipAddress", source = "ip")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "code", ignore = true)
    LoginLog convertLoginLogCreateReq2Account(CreateLoginLogRequest request);


    LoginLogBean convertLoginLog2LoginLogBean(LoginLog loginLog);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "accountCode", ignore = true)
    LoginLog convertLoginLogUpdateReq2LoginLog(Long id, UpdateLoginLogRequest request);


    MobileAreaCode convertMobileAreaCodeCreateReq2MobileArea(CreateMobileAreaRequest request);


    MobileAreBean convertMobileArea2MobileAreaBean(MobileAreaCode item);


    MobileAreaCode convertMobileAreaUpdateReq2MobileArea(Long id, UpdateMobileAreaRequest request);


    PhoneAuth convertPhoneAuthCreateReq2PhoneAuth(CreatePhoneAuthRequest request);


    UserHeader convertUserHeaderCreateReq2UserHeader(CreateUserHeaderRequest request);


    UserDetailsBean convertUser2UserBean(User user);


    User convertUserCreateReq2User(CreateUserRequest request);


    @Mapping(target = "code", expression = "java(top.easyblog.support.util.IdGenerator.generateRandomCode(6))")
    @Mapping(target = "templateValueConfigId", source = "templateValueConfigId")
    MessageConfig buildMessageConfig(CreateMessageConfigRequest request, Long templateValueConfigId);

    @Mapping(target = "id", source = "id")
    MessageConfig buildMessageConfig(UpdateMessageConfigRequest request, Long id);

    TemplateValueConfig buildTemplateConfig(CreateTemplateValueConfigRequest request);

    @Mapping(target = "id", source = "id")
    TemplateValueConfig buildTemplateConfig(UpdateTemplateValueConfigRequest request, Long id);

    @Mappings({
            @Mapping(target = "templateValueConfigType", source = "templateValueConfig.type"),
            @Mapping(target = "expression", source = "templateValueConfig.expression"),
            @Mapping(target = "url", source = "templateValueConfig.url"),
            @Mapping(target = "type", source = "messageConfig.type"),
            @Mapping(target = "deleted", source = "messageConfig.deleted"),
            @Mapping(target = "createTime", expression = "java(messageConfig.getCreateTime().getTime()/1000)"),
            @Mapping(target = "updateTime", expression = "java(messageConfig.getUpdateTime().getTime()/1000)")
    })
    MessageConfigBean buildMessageConfigBean(MessageConfig messageConfig, TemplateValueConfig templateValueConfig);

    MessageConfigRule buildMessageConfigRule(CreateMessageConfigRuleRequest request);

    @Mapping(target = "id", source = "id")
    MessageConfigRule buildMessageConfigRule(UpdateMessageConfigRuleRequest request, Long id);

    @Mapping(target = "createTime", expression = "java(messageConfigRule.getCreateTime().getTime()/1000)")
    @Mapping(target = "updateTime", expression = "java(messageConfigRule.getUpdateTime().getTime()/1000)")
    MessageConfigRuleBean buildMessageConfigRuleBean(MessageConfigRule messageConfigRule);


    MessageTemplateBean convertMessageTemplate2MessageTemplateBean(MessageTemplate template);


    MessageConfigContext buildMessageConfigContext(BusinessMessageRecord msg, MessageConfigRuleBean messageConfigRule,
            MessageTemplateBean messageTemplate, List<MessageConfigBean> messageConfigs);
}
