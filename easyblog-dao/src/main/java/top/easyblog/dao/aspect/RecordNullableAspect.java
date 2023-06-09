package top.easyblog.dao.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.easyblog.support.util.PropertiesUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: frank.huang
 * @date: 2023-02-25 20:38
 */
@Aspect
@Component
public class RecordNullableAspect {

    @Pointcut("@annotation(top.easyblog.dao.annotation.RecordNullable)")
    public void response() {
    }


    @Around("response()")
    public Object beforeQueryCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        AtomicBoolean allArgEmpty = new AtomicBoolean(true);
        Arrays.stream(args).filter(Objects::nonNull).forEach(arg -> {
            try {
                allArgEmpty.set(allArgEmpty.get() & PropertiesUtils.allFieldsAreNull(arg));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        Object obj = pjp.proceed(pjp.getArgs());
        return Objects.equals(allArgEmpty.get(), Boolean.TRUE) ? null : obj;
    }
}
