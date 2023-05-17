package top.easyblog.dao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;

import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2023-02-25 20:38
 */
@Aspect
@Component
public class ParamNonNullAspect {

    @Pointcut("execution(public * top.easyblog.dao.atomic..*.*(..))")
    public void checkParamNouNull() {
    }


    @Before("checkParamNouNull()")
    public void beforeQueryCheck(JoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args == null || args.length == 0) {
            throw new BusinessException(EasyResultCode.DB_OPERATE_RECORD_NOT_ALLOW_NULL);
        }

        for (Object arg : args) {
            Optional.ofNullable(arg).orElseThrow(() -> new BusinessException(EasyResultCode.DB_OPERATE_RECORD_NOT_ALLOW_NULL));
        }
    }
}
