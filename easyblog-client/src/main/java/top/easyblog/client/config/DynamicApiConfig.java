package top.easyblog.client.config;

import org.springframework.context.annotation.Bean;

import feign.Contract;
import feign.codec.Decoder;
import feign.codec.StringDecoder;

/**
 * 动态URL Feign配置
 */
public class DynamicApiConfig extends FeignConfig {

    @Bean
    public Decoder decoder() {
        return new StringDecoder();
    }

    
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

}
