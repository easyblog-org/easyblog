package top.easyblog.core.strategy.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.easyblog.client.clients.GiteeClient;
import top.easyblog.client.model.request.QueryGiteeAuthTokenRequest;
import top.easyblog.client.model.response.dto.GiteeAuthDTO;
import top.easyblog.client.model.response.dto.GiteeAuthTokenDTO;
import top.easyblog.common.bean.AuthenticationDetailsBean;
import top.easyblog.common.bean.AuthorizationBean;
import top.easyblog.common.constant.LoginConstants;
import top.easyblog.common.enums.IdentifierType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.login.AuthCallbackRequest;
import top.easyblog.common.request.login.OauthRequest;
import top.easyblog.common.request.login.RegisterUserRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.strategy.oauth2.config.GiteeAuthProperties;
import top.easyblog.service.ILoginService;
import top.easyblog.service.strategy.IAuthStrategy;
import top.easyblog.service.strategy.IOauthStrategy;
import top.easyblog.support.util.JsonUtils;

import java.util.Optional;

/**
 * Gitee第三方接入官方文档：<a href="https://gitee.com/api/v5/oauth_doc#/list-item-1">...</a>
 *
 * @author: frank.huang
 * @date: 2022-03-06 00:33
 */
@Slf4j
@Component
public class GiteeAuthStrategyImpl implements IAuthStrategy<GiteeAuthDTO>, IOauthStrategy {


    @Autowired
    private GiteeClient giteeClient;

    @Autowired
    private GiteeAuthProperties giteeAuthProperties;

    @Autowired
    private ILoginService loginService;


    @Override
    public Integer getIdentifierType() {
        return IdentifierType.GITEE.getSubCode();
    }

    @Override
    public String getAccessToken(String code) {
        GiteeAuthTokenDTO accessToken = giteeClient.getAccessToken(QueryGiteeAuthTokenRequest.builder()
                .clientId(giteeAuthProperties.getClientId())
                .clientSecret(giteeAuthProperties.getClientSecret())
                .code(code)
                .grantType(LoginConstants.COMMON_GRANT_TYPE)
                .redirectUri(giteeAuthProperties.getRedirectUrl())
                .build());
        log.info("Get Gitee access_token: {}", JsonUtils.toJSONString(accessToken));        
        return Optional.ofNullable(accessToken).map(GiteeAuthTokenDTO::getAccessToken)
        .orElseThrow(() -> new BusinessException(EasyResultCode.REQUEST_GITEE_ACCESS_TOKEN_FAILED));
    }

    @Override
    public String getOpenId(String accessToken) {
        return null;
    }

    @Override
    public String refreshToken(String code) {
        return null;
    }

    @Override
    public GiteeAuthDTO getUserInfo(String accessToken) {
        GiteeAuthDTO giteeUserInfo = giteeClient.getGiteeUserInfo(accessToken);
        log.info("Get gitee user info: {}", JsonUtils.toJSONString(giteeUserInfo));
        return Optional.of(giteeUserInfo).orElseThrow(() -> new BusinessException(EasyResultCode.REQUEST_GITEE_USER_INFO_FAILED));
    }

    @Override
    public AuthorizationBean authorize(OauthRequest request) {
        String authorizationUr = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code", giteeAuthProperties.getAuthorizeUrl(),
                giteeAuthProperties.getClientId(), giteeAuthProperties.getRedirectUrl());
        return AuthorizationBean.builder().authorizationUrl(authorizationUr).build();
    }

    @Override
    public AuthorizationBean callback(AuthCallbackRequest callback) {
        String accessToken = getAccessToken(callback.getCode());
        return Optional.ofNullable(accessToken).map(token -> {
            GiteeAuthDTO userInfo = getUserInfo(token);
            AuthenticationDetailsBean authenticationDetailsBean = loginService.register(RegisterUserRequest.builder()
                    .identifierType(IdentifierType.GITEE.getSubCode())
                    .identifier(userInfo.getId())
                    .userHeader(CreateUserHeaderRequest.builder().headerImgUrl(userInfo.getAvatarUrl()).build())
                    .build());
            return AuthorizationBean.builder().user(authenticationDetailsBean.getUser()).build();
        }).orElseThrow(() -> new BusinessException(EasyResultCode.REQUEST_GITEE_ACCESS_TOKEN_FAILED));
    }
}
