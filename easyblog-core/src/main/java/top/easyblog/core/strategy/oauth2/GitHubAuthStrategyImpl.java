package top.easyblog.core.strategy.oauth2;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import top.easyblog.client.clients.GitHubClient;
import top.easyblog.client.clients.GitHubOpenApiClient;
import top.easyblog.client.model.request.QueryGitHubAuthTokenRequest;
import top.easyblog.client.model.response.dto.GitHubAuthDTO;
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
import top.easyblog.core.strategy.oauth2.config.GitHubAuthProperties;
import top.easyblog.service.ILoginService;
import top.easyblog.service.strategy.IAuthStrategy;
import top.easyblog.service.strategy.IOauthStrategy;
import top.easyblog.support.util.JsonUtils;

import java.util.Optional;

/**
 * GitHub第三方接入官方文档：<a href="https://developer.github.com/apps/building-github-apps/identifying-and-authorizing-users-for-github-apps/">...</a>
 *
 * @author frank.huang
 * @date 2022/02/21 16:18
 */
@Slf4j
@Component
public class GitHubAuthStrategyImpl implements IAuthStrategy<GitHubAuthDTO>, IOauthStrategy {

    @Autowired
    private GitHubAuthProperties gitHubAuthProperties;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private GitHubOpenApiClient gitHubOpenApiClient;

    @Override
    public Integer getIdentifierType() {
        return IdentifierType.GITHUB.getSubCode();
    }

    @Override
    public String getAccessToken(String code) {
        MultiValueMap<String, String> accessToken = gitHubClient.getAccessToken(QueryGitHubAuthTokenRequest.builder()
                .clientId(gitHubAuthProperties.getClientId())
                .clientSecret(gitHubAuthProperties.getClientSecret())
                .code(code)
                .grantType(LoginConstants.COMMON_GRANT_TYPE)
                .build());
        log.info("Get GitHub access_token: {}", JsonUtils.toJSONString(accessToken));        
        return Optional.ofNullable(accessToken).map(item -> {
            return Iterables.getFirst(item.get("accessToken"), null);
        }).orElseThrow(() -> new BusinessException(EasyResultCode.REQUEST_GITHUB_ACCESS_TOKEN_FAILED));
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
    public GitHubAuthDTO getUserInfo(String accessToken) {
        GitHubAuthDTO gitHubAuthBean = gitHubOpenApiClient.getGithubUserInfo(String.format("token %s", accessToken));
        log.info("Get github user information: {}", JsonUtils.toJSONString(gitHubAuthBean));
        return Optional.ofNullable(gitHubAuthBean).orElseThrow(()->new BusinessException(EasyResultCode.REQUEST_GITHUB_USER_INFO_FAILED));
    }


    @Override
    public AuthorizationBean authorize(OauthRequest request) {
        String authorizationUr = String.format("%s?client_id=%s&state=STATE&redirect_uri=%s", gitHubAuthProperties.getAuthorizeUrl(),
                gitHubAuthProperties.getClientId(), gitHubAuthProperties.getRedirectUrl());
        return AuthorizationBean.builder().authorizationUrl(authorizationUr).build();
    }

    @Override
    public AuthorizationBean callback(AuthCallbackRequest callback) {
        String accessToken = getAccessToken(callback.getCode());
        return Optional.ofNullable(accessToken).map(token -> {
            GitHubAuthDTO userInfo = getUserInfo(token);
            AuthenticationDetailsBean authenticationDetailsBean = loginService.register(RegisterUserRequest.builder()
                    .identifierType(IdentifierType.GITHUB.getSubCode())
                    .identifier(userInfo.getId())
                    .userHeader(CreateUserHeaderRequest.builder().headerImgUrl(userInfo.getAvatarUrl()).build())
                    .build());
            return AuthorizationBean.builder().user(authenticationDetailsBean.getUser()).build();
        }).orElseThrow(() -> new BusinessException(EasyResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS));
    }
}
