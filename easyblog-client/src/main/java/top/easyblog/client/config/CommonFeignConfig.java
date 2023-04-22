package top.easyblog.client.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
public class CommonFeignConfig extends FeignConfig {

    @Bean
    public ErrorDecoder error() {
        return (s, response) -> {
            throw new BusinessException(EasyResultCode.REMOTE_INVOKE_FAIL, "远程调用失败");
        };
    }

}
