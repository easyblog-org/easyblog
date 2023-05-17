package top.easyblog.client.clients;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import feign.Headers;
import feign.RequestLine;
import top.easyblog.client.config.DynamicApiConfig;
import top.easyblog.client.internal.Verify;
import top.easyblog.common.response.BaseResponse;

/**
 *
 * @author: frank.huang
 * @date: 2022-05-11 11:54
 */
@FeignClient(name = "common", configuration = DynamicApiConfig.class)
public interface CommonClient extends Verify {

    /**
     * get请求
     *
     * @param uri   动态请求地址
     * @return
     */
    @RequestLine(value="GET")
    BaseResponse<?> get(URI uri);

    /***
     * POST请求
     * @param uri 动态请求地址
     * @return
     */
    @RequestLine(value="POST")
    @Headers(value = "Content-Type: application/json")
    BaseResponse<?> post(URI uri);

}
