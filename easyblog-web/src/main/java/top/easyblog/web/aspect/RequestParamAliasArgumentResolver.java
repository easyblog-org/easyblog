package top.easyblog.web.aspect;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import top.easyblog.common.constant.Constants;
import top.easyblog.web.annotation.RequestParamAlias;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2022-02-12 17:10
 */
@Slf4j
public class RequestParamAliasArgumentResolver extends AbstractCustomizeArgumentResolver {
    /**
     * 匹配_加任意一个字符
     */
    private static final Pattern UNDER_LINE_PATTERN = Pattern.compile("_(\\w)");

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestParamAlias.class);
    }


    /**
     * 处理参数
     *
     * @param parameter
     * @param webRequest
     * @return
     */
    @Override
    public Object handleParameterNames(MethodParameter parameter, NativeWebRequest webRequest) {
        Object obj = BeanUtils.instantiateClass(parameter.getParameterType());
        Map<String, Field> fieldMap = Arrays.stream(obj.getClass().getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, Function.identity(), (x, y) -> x));

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Iterator<String> paramNames = webRequest.getParameterNames();
        while (paramNames.hasNext()) {
            String originalParam = paramNames.next();
            String formattedParam = underLineToCamel(originalParam);
            if (wrapper.isWritableProperty(formattedParam)) {
                Field field = fieldMap.get(formattedParam);
                Object value = webRequest.getParameter(originalParam);
                if (Objects.nonNull(field) && List.class.isAssignableFrom(field.getType())) {
                    value = Lists.newArrayList(Objects.requireNonNull(StringUtils.split((String) value, Constants.COMMA)));
                }
                log.debug("Handle request param underline to camel ==> {}={}", originalParam, value);
                wrapper.setPropertyValue(formattedParam, value);
            }
        }
        return obj;
    }

    /**
     * 下换线转驼峰
     *
     * @param source
     * @return
     */
    private String underLineToCamel(String source) {
        Matcher matcher = UNDER_LINE_PATTERN.matcher(source);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
