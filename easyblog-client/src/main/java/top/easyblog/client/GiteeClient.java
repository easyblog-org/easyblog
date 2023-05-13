package top.easyblog.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import top.easyblog.client.config.CommonFeignConfig;
import top.easyblog.client.internal.Verify;
import top.easyblog.client.model.request.QueryGiteeAuthTokenRequest;
import top.easyblog.client.model.response.dto.GiteeAuthDTO;
import top.easyblog.client.model.response.dto.GiteeAuthTokenDTO;

/**
 * Gitee第三方接入官方文档：https://gitee.com/api/v5/oauth_doc#/list-item-1
 *
 * @author: frank.huang
 * @date: 2022-03-06 11:54
 */
@FeignClient(name = "gitee", url = "${urls.gitee}", configuration = CommonFeignConfig.class)
public interface GiteeClient extends Verify {

    /**
     * 获取Gitee access_token
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/oauth/token")
    @Headers({"User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36)"})
    GiteeAuthTokenDTO getAccessToken(@RequestBody QueryGiteeAuthTokenRequest request);

    /**
     * Gitee获取收钱用户信息
     *
     * @param accessToken
     * @return
     */
    @GetMapping(value = "/api/v5/user")
    GiteeAuthDTO getGiteeUserInfo(@RequestParam("access_token") String accessToken);
}
