package top.easyblog.client.config;

import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import feign.QueryMapEncoder;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import top.easyblog.client.http.converter.CustomGsonHttpMessageConverter;
import top.easyblog.client.http.encoder.CamelToUnderscoreEncoder;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.support.util.JsonUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
public class OnBukaFeignConfig extends FeignConfig {


    @Value("${sms.onbuka.api-key}")
    private String apiKey
            ;
    @Value("${sms.onbuka.api-secret}")
    private String apiPwd;

    public OnBukaFeignConfig() {
        customGsonConverters = new CustomGsonHttpMessageConverter();
        customGsonConverters.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM, new MediaType("application", "*+json")));
        customGsonConverters.setGson(JsonUtils.getGson());
    }

    @Override
    public RequestInterceptor requestHeader() {
        String datetime = String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        return requestTemplate -> requestTemplate
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Sign", SecureUtil.md5(apiKey.concat(apiPwd).concat(datetime)))
                .header("Timestamp", datetime)
                .header("Api-Key", apiKey);
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
