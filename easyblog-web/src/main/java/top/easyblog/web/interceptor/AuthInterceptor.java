package top.easyblog.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.web.config.properties.AuthProperties;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * 请求认证拦截器
 *
 * @author: frank.huang
 * @date: 2023-02-25 13:15
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AuthProperties authProperties;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (noNeedValidAuth(request)) {
            return true;
        }
        String token = request.getHeader(Constants.AUTH_TOKEN);
        if (StringUtils.isBlank(token)) {
            log.info("Token not found in request header.");
            throw new BusinessException(EasyResultCode.AUTH_TOKEN_NOT_FOUND);
        }
        Long expireTime = redisTemplate.getExpire(token);
        log.info("Get redis auth token {}={}", token, expireTime);
        boolean tokenExists = Objects.nonNull(expireTime) && expireTime > 0;
        if (Objects.equals(tokenExists, Boolean.FALSE)) {
            throw new BusinessException(EasyResultCode.AUTH_EXPIRED);
        }
        return true;
    }

    private boolean noNeedValidAuth(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !StringUtils.contains("/admin", requestURI) ||
                Optional.ofNullable(authProperties.getWhiteList()).map(whiteList -> whiteList.contains(requestURI)).orElse(true);
    }
}
