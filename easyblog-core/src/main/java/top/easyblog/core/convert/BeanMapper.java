package top.easyblog.core.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import top.easyblog.common.bean.*;
import top.easyblog.common.request.account.CreateAccountRequest;
import top.easyblog.common.request.account.UpdateAccountRequest;
import top.easyblog.common.request.article.*;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.login.AdminLoginRequest;
import top.easyblog.common.request.login.UserLoginRequest;
import top.easyblog.common.request.login.LoginRequest;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;
import top.easyblog.common.request.loginlog.UpdateLoginLogRequest;
import top.easyblog.common.request.message.config.CreateMessageConfigRequest;
import top.easyblog.common.request.message.config.CreateTemplateValueConfigRequest;
import top.easyblog.common.request.message.config.UpdateMessageConfigRequest;
import top.easyblog.common.request.message.config.UpdateTemplateValueConfigRequest;
import top.easyblog.common.request.message.record.CreateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.record.UpdateBusinessMessageRecordRequest;
import top.easyblog.common.request.message.rule.CreateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.UpdateMessageConfigRuleRequest;
import top.easyblog.common.request.message.rule.UpdateMessagePushRuleRequest;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.common.request.phoneauth.CreatePhoneAuthRequest;
import top.easyblog.common.request.user.CreateUserAccountRequest;
import top.easyblog.common.request.user.CreateUserRequest;
import top.easyblog.common.request.user.UpdateUserAccountRequest;
import top.easyblog.common.request.user.UpdateUserRequest;
import top.easyblog.dao.auto.model.*;
import top.easyblog.dao.mongo.model.ArticleContent;
import top.easyblog.support.context.BusinessMessageRecordContext;
import top.easyblog.support.context.MessageConfigContext;

/**
 * @author: frank.huang
 * @date: 2023-04-22 15:53
 */
@Mapper(componentModel = "spring")
public interface BeanMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "code", ignore = true)
    })
    Account convertAccountCreateReq2Account(CreateAccountRequest request);

    AccountBean convertAccount2AccountBean(Account account);

    @Mappings({
            @Mapping(target = "userCode", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "id", source = "accountId")
    })
    Account convertAccountUpdateReq2Account(Long accountId, UpdateAccountRequest request);

    @Mappings({
            @Mapping(target = "ipAddress", source = "ip"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "code", ignore = true),
    })
    LoginLog convertLoginLogCreateReq2Account(CreateLoginLogRequest request);

    LoginLogBean convertLoginLog2LoginLogBean(LoginLog loginLog);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "accountCode", ignore = true)
    })
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

    @Mapping(target = "createTime", expression = "java(valueConfig.getCreateTime().getTime()/1000)")
    @Mapping(target = "updateTime", expression = "java(valueConfig.getUpdateTime().getTime()/1000)")
    TemplateValueConfigBean convertTemplateValueConfig2TemplateValueConfigBean(TemplateValueConfig valueConfig);

    @Mappings({
            @Mapping(target = "templateValueConfig", expression = "java(convertTemplateValueConfig2TemplateValueConfigBean(templateValueConfig))"),
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

    @Mapping(target = "status", source = "template.status")
    MessageTemplateBean convertMessageTemplate2MessageTemplateBean(MessageTemplate template);

    @Mappings({
            @Mapping(target = "businessMessageRecordId", source = "msg.id"),
            @Mapping(target = "businessModule", source = "msg.businessModule"),
            @Mapping(target = "businessEvent", source = "msg.businessEvent"),
            @Mapping(target = "businessMessage", source = "msg.businessMessage"),
            @Mapping(target = "group", source = "messageConfigRule.msgGroup"),
            @Mapping(target = "priority", source = "messageConfigRule.priority"),
            @Mapping(target = "channel", source = "messageConfigRule.channel"),
            @Mapping(target = "msgType", source = "messageTemplate.msgType"),
            @Mapping(target = "shieldType", source = "messageTemplate.shieldType"),
            @Mapping(target = "msgTemplateContent", source = "messageTemplate.msgContent"),
            @Mapping(target = "title", source = "messageTemplate.name"),
            @Mapping(target = "configs", source = "messageConfigs")
    })
    MessageConfigContext buildMessageConfigContext(BusinessMessageRecordContext msg,
                                                   MessageConfigRuleBean messageConfigRule,
                                                   MessageTemplateBean messageTemplate, List<MessageConfigBean> messageConfigs);

    BusinessMessageRecord convertMessageSendRecordCreateReq2MessageSendRecord(
            CreateBusinessMessageRecordRequest request);

    @Mapping(target = "isSync", source = "isSync")
    BusinessMessageRecordContext convertMessageSendRecord2MessageSendRecordContext(BusinessMessageRecord record,
                                                                                   Boolean isSync);

    @Mapping(target = "createTime", expression = "java(record.getCreateTime().getTime()/1000)")
    @Mapping(target = "updateTime", expression = "java(record.getUpdateTime().getTime()/1000)")
    BusinessMessageRecordBean convertBusinessMessageRecord2BusinessMessageRecordBean(BusinessMessageRecord record);

    @Mapping(target = "templateCode", expression = "java(top.easyblog.support.util.IdGenerator.generateRandomCode(12))")
    MessageTemplate convertBusinessMessageRecordCreateReqBusinessMessageRecord(
            CreateMessageTemplateRequest request);

    MessageTemplate convertBusinessMessageRecordUpdateReqBusinessMessageRecord(Long id,
                                                                               UpdateMessageTemplateRequest request);

    @Mapping(target = "id", source = "id")
    BusinessMessageRecord convertMessageSendRecordUpdateReq2MessageSendRecord(Long id,
                                                                              UpdateBusinessMessageRecordRequest request);

    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "contentId", source = "contentId"),
            @Mapping(target = "status", expression = "java(request.getStatus().toUpperCase())"),
            @Mapping(target = "code", expression = "java(top.easyblog.support.util.IdGenerator.getSnowflakeNextId())"),
    })
    Article convertArticleCreateReq2Article(CreateArticleRequest request, String contentId);

    @Mapping(target = "id", source = "articleId")
    @Mapping(target = "contentId", source = "contentId")
    @Mapping(target = "status", expression = "java(java.util.Optional.ofNullable(request.getStatus()).map(String::toUpperCase).orElse(null))")
    Article convertArticleUpdateReq2Article(UpdateArticleRequest request, Long articleId, String contentId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "content")
    ArticleContent buildArticleContent(String content);

    ArticleCategoryBean convertArticleCategory2ArticleCategoryBean(ArticleCategory articleCategory);

    @Mapping(target = "id", source = "id")
    ArticleCategory convertArticleCategoryUpdateReq2AeticleCategory(Long id, UpdateArticleCategoryRequest request);

    ArticleCategory convertArticleCategoryCreateReq2AeticleCategory(CreateArticleCategoryRequest request);

    CreateUserRequest buildUserCreateRequest(CreateUserAccountRequest request);

    @Mapping(target = "identifier", source = "request.email")
    @Mapping(target = "credential", source = "request.password")
    @Mapping(target = "userCode", source = "code")
    CreateAccountRequest buildCreateAccountRequest(CreateUserAccountRequest request, String code);

    @Mapping(target = "identifier", source = "email")
    @Mapping(target = "credential", source = "password")
    UpdateAccountRequest buildAccountUpdateRequest(UpdateUserAccountRequest request);

    UpdateUserRequest buildUserCreateRequest(UpdateUserAccountRequest request);

    @Mapping(target = "configIds", source = "configIds")
    UpdateMessageConfigRuleRequest buildMessageRuleConfigUpdateReq(UpdateMessagePushRuleRequest request, String configIds);

    @Mapping(target = "identifierType", expression = "java(top.easyblog.common.enums.IdentifierType.E_MAIL.getSubCode())")
    @Mapping(target = "identifier", source = "request.email")
    @Mapping(target = "credential", source = "request.password")
    LoginRequest buildUserLoginRequest(AdminLoginRequest request);


    LoginRequest buildUserLoginRequest(UserLoginRequest request);

    @Mapping(target = "ip", source = "request.ip")
    @Mapping(target = "device", source = "request.device")
    @Mapping(target = "operationSystem", source = "request.operationSystem")
    @Mapping(target = "location", source = "request.location")
    @Mapping(target = "userCode", source = "accountBean.userCode")
    @Mapping(target = "accountCode", source = "accountBean.code")
    @Mapping(target = "status", ignore = true)
    CreateLoginLogRequest buildUserSignLogReqeust(AdminLoginRequest request, AccountBean accountBean);

    @Mapping(target = "ip", source = "request.ip")
    @Mapping(target = "device", source = "request.device")
    @Mapping(target = "operationSystem", source = "request.operationSystem")
    @Mapping(target = "location", source = "request.location")
    @Mapping(target = "userCode", source = "accountBean.userCode")
    @Mapping(target = "accountCode", source = "accountBean.code")
    @Mapping(target = "status", ignore = true)
    CreateLoginLogRequest buildUserSignLogReqeust(UserLoginRequest request, AccountBean accountBean);


    ArticleContentBean convertArticleContent2Bean(ArticleContent item);

    ArticleEvent buildArticleEvent(CreateArticleEventRequest request);
}
