package top.easyblog.core.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.LoginStatus;
import top.easyblog.core.LoginLogService;
import top.easyblog.core.LoginService;
import top.easyblog.dao.atomic.AtomicLoginLogService;

import static top.easyblog.common.constant.LoginConstants.LOGIN_TOKEN_KEY_PREFIX;


/**
 * @author: frank.huang
 * @date: 2023-10-01 20:31
 */
@Slf4j
@Component
public class UserLoginExpireListener extends KeyExpirationEventMessageListener {

    @Autowired
    private LoginService loginService;


    public UserLoginExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    /**
     * 针对Redis key失效进行处理
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        String tokenPrefix = LOGIN_TOKEN_KEY_PREFIX.substring(0, LOGIN_TOKEN_KEY_PREFIX.lastIndexOf(":"));
        if (StringUtils.isNotBlank(expireKey) && expireKey.contains(tokenPrefix)) {
            loginService.asyncUpdateLoginStatusOffline(expireKey);
            log.info("Process expire token {} event successfully!", expireKey);
        }
    }
}
