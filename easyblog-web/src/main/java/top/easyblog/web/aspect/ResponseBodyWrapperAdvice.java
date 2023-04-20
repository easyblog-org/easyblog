package top.easyblog.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.easyblog.common.response.BaseResponse;
import top.easyblog.support.util.JsonUtils;
import top.easyblog.web.annotation.ResponseWrapper;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 响应增强类
 *
 * @author: frank.huang
 * @date: 2021-11-01 18:28
 */
@Slf4j
@ControllerAdvice
public class ResponseBodyWrapperAdvice implements ResponseBodyAdvice<Object>, Ordered {
    @Override
    public boolean supports(MethodParameter returnType, @NotNull Class converterType) {
        return returnType.getAnnotatedElement().isAnnotationPresent(ResponseWrapper.class);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NotNull MethodParameter returnType,
                                  @NotNull MediaType mediaType,
                                  @NotNull Class selectedClassType,
                                  @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        if (Objects.nonNull(body) && body instanceof BaseResponse) {
            log.info("Writing " + JsonUtils.toJSONString(body));
            return body;
        }
        BaseResponse<Object> responseBody = BaseResponse.ok(body);
        log.info("Writing " + JsonUtils.toJSONString(responseBody));
        return responseBody;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
