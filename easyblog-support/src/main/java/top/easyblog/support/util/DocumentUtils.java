package top.easyblog.support.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentUtils {

    private static final Configuration defaultConfiguration = Configuration.defaultConfiguration()
            .addOptions(Option.SUPPRESS_EXCEPTIONS)
            .addOptions(Option.ALWAYS_RETURN_LIST)
            .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);

    private DocumentUtils() {
    }

    public static String parse(String json, String expression) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(expression)) {
            log.info("json or expression is blank");
            return null;
        }
        Object document = defaultConfiguration.jsonProvider().parse(json);
        Object values = JsonPath.read(document, expression);
        return StringUtils.joinWith(",", values);
    }

}
