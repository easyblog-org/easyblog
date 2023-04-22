package top.easyblog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import top.easyblog.client.config.CommonFormFeignConfig;
import top.easyblog.client.internal.Verify;
import top.easyblog.client.model.request.QueryGitHubAuthTokenRequest;

/**
 * @author: frank.huang
 * @date: 2022-02-27 11:41
 */
@FeignClient(name = "github", url = "${urls.github}", configuration = CommonFormFeignConfig.class)
public interface GitHubClient extends Verify {

    /**
     * 获取GitHub请求token
     *
     * @return
     */
    @PostMapping(value = "/login/oauth/access_token")
    MultiValueMap<String, String> getAccessToken(QueryGitHubAuthTokenRequest request);

}
