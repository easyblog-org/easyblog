package top.easyblog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.easyblog.client.config.CommonFeignConfig;
import top.easyblog.client.internal.Verify;
import top.easyblog.common.response.BaseResponse;

/**
 *
 * @author: frank.huang
 * @date: 2022-05-11 11:54
 */
@FeignClient(name = "common", configuration = CommonFeignConfig.class)
public interface CommonClient extends Verify {

    /**
     * get请求
     *
     * @param url
     * @return
     */
    @GetMapping("{url}")
    BaseResponse<?> get(@PathVariable("url") String url);

    @PostMapping(value = "{url}")
    BaseResponse<?> post(@PathVariable("url") String ur);

}
