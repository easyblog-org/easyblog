package top.easyblog.support.executor;

import java.util.concurrent.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomExecutor {

    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 10;
    private static final int DEFAULT_QUEUE_CAPACITY = 1024;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 0;
    private static final TimeUnit DEFAULT_KEEP_ALIVE_TIME_UNIT = TimeUnit.MICROSECONDS;
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(
            DEFAULT_QUEUE_CAPACITY);

    /**
     * 消息推送处理线程
     * 
     * @return
     */
    @Bean("messageProcessor")
    public ExecutorService messageProcessor() {
        return new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE,
                DEFAULT_KEEP_ALIVE_TIME, DEFAULT_KEEP_ALIVE_TIME_UNIT, DEFAULT_WORK_QUEUE,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
