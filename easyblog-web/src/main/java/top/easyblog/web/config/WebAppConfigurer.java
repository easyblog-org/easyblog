package top.easyblog.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.web.aspect.RequestParamAliasArgumentResolver;
import top.easyblog.web.config.converter.CustomGsonHttpMessageConverter;
import top.easyblog.web.interceptor.RequestLogInterceptor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2021-11-06 13:08
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    private RequestLogInterceptor requestLogInterceptor;


    @Autowired
    private GsonHttpMessageConverter customConverters;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, customConverters);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //将Get  请求的下划线转化成驼峰样式
        argumentResolvers.add(new RequestParamAliasArgumentResolver());
    }

    @Bean
    public GsonHttpMessageConverter customConverters() {
        return new CustomGsonHttpMessageConverter();
    }

    @Bean
    public ErrorViewResolver errorViewResolver() {
        return (request, status, model) -> {
            throw new BusinessException(EasyResultCode.FAIL);
        };
    }
}
