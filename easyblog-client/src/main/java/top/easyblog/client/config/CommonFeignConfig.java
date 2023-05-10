package top.easyblog.client.config;

import com.google.common.collect.Lists;
import feign.QueryMapEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import top.easyblog.client.http.converter.CustomGsonHttpMessageConverter;
import top.easyblog.client.http.encoder.CamelToUnderscoreEncoder;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.support.util.JsonUtils;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
public class CommonFeignConfig extends FeignConfig {


    public CommonFeignConfig() {
        customGsonConverters = new CustomGsonHttpMessageConverter();
        customGsonConverters.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM, new MediaType("application", "*+json")));
        customGsonConverters.setGson(JsonUtils.getGson());
    }

    @Bean
    public ErrorDecoder error() {
        return (s, response) -> {
            throw new BusinessException(EasyResultCode.REMOTE_INVOKE_FAIL, "远程调用失败");
        };
    }

    @Bean
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters(false, Lists.newArrayList(
                customGsonConverters
        ))));
    }

    @Bean
    public Encoder encoder() {
        return new SpringEncoder(() -> new HttpMessageConverters(false, Lists.newArrayList(customGsonConverters)));
    }

    @Bean
    public QueryMapEncoder queryMapEncoder() {
        return new CamelToUnderscoreEncoder();
    }

}
