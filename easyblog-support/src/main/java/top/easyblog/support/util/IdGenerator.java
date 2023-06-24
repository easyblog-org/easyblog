package top.easyblog.support.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.Random;
import java.util.UUID;

/**
 * @author: frank.huang
 * @date: 2021-11-01 19:12
 */
public class IdGenerator {

    private static final String ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHLJKLMNOPQRSTUVWXYZ";

    private static final Snowflake SNOWFLAKE = IdUtil.createSnowflake(1, 1);

    public static final int DEFAULT_LENGTH = 10;

    public static final int SHORT_LENGTH = 6;


    private IdGenerator() {
    }

    public static String getRequestId() {
        return getUUID();
    }

    public static String getTraceId() {
        return getUUID() + ((int) ((Math.random() * 9 + 1) * 100000));
    }


    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }


    public static String getSnowflakeNextId() {
        return SNOWFLAKE.nextIdStr();
    }

}
