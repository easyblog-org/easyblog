package top.easyblog.support.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2021-12-04 22:23
 */
public class JsonUtils {

    private static final Gson LOWER_CASE_GSON;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        LOWER_CASE_GSON = gsonBuilder.create();
    }

    private JsonUtils() {
    }

    public static Gson getGson() {
        return LOWER_CASE_GSON;
    }

    /**
     * 转换对象为json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String toJSONString(Object obj) {
        return LOWER_CASE_GSON.toJson(obj);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param jsonStr json字符串
     * @param clazz   目标对象的Class类型
     * @param <T>     目标对象类型泛型参数
     * @return 目标类型对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return LOWER_CASE_GSON.fromJson(jsonStr, clazz);
    }

    /**
     * 将Json字符串转换为Map
     *
     * @param json
     * @return
     */
    @SuppressWarnings("all")
    public static Map<String, Object> jsonToMap(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Map.class);
    }
}
