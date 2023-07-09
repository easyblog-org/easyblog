package top.easyblog.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.BaseRequest;
import top.easyblog.common.response.EasyResultCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 14:45
 */
@Aspect
@Component
public class ApiRequestAspect {

    @Pointcut("(execution(public * top.easyblog.web.controller..*.*(..)) ||" +
            "execution(public * top.easyblog.web.api.*.*(..))) && " +
            "(@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object processApiRequest(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Arrays.stream(args).filter(arg -> Objects.nonNull(arg) && arg instanceof BaseRequest).forEach(arg -> {
            if (!((BaseRequest) arg).validate()) {
                throw new BusinessException(EasyResultCode.PARAMETER_VALIDATE_FAILED, "请求参数不合法！");
            }
        });
        return pjp.proceed(pjp.getArgs());
    }
}
