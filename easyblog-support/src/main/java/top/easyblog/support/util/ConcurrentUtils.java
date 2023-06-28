package top.easyblog.support.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import top.easyblog.common.constant.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 并发工具类
 *
 * @author: frank.huang
 * @date: 2023-06-25 22:22
 */
@Slf4j
public class ConcurrentUtils {

    private ConcurrentUtils() {
    }

    private static final int DEFAULT_MIN_CORE_POOL_SIZE = 8;
    private static final int DEFAULT_MAX_POOL_SIZE = 20;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 60;
    private static final TimeUnit DEFAULT_POOL_KEEP_ALIVE_TIME_TIMEUNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> DEFAULT_POOL_WORK_QUEUE = new LinkedBlockingQueue<>(1024);

    private static final ExecutorService DEFAULT_THREAD_POOL = new ThreadPoolExecutor(DEFAULT_MIN_CORE_POOL_SIZE,
            DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME, DEFAULT_POOL_KEEP_ALIVE_TIME_TIMEUNIT,
            DEFAULT_POOL_WORK_QUEUE,
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void executeTaskInBlockModel(List<Runnable> tasks, ExecutorService threadPool) {
        CountDownLatch cdl = new CountDownLatch(tasks.size());
        String requestId = MDC.get(Constants.REQUEST_ID);
        Consumer<Runnable> consumer = task -> {
            MDC.put(Constants.REQUEST_ID, requestId);
            try {
                task.run();
            } catch (Exception e) {
                log.info("Task execute failed:", e.fillInStackTrace());
                throw e;
            } finally {
                cdl.countDown();
                MDC.clear();
            }
        };

        if (Objects.isNull(threadPool)) {
            threadPool = DEFAULT_THREAD_POOL;
        }
        ExecutorService pool = threadPool;
        tasks.stream().filter(Objects::nonNull).forEach(task -> {
            pool.submit(() -> consumer.accept(task));
        });

        try {
            cdl.await();
        } catch (InterruptedException e) {
            log.info("Task execute failed:", e.fillInStackTrace());
            throw new RuntimeException(e);
        }
    }

    public static void executeTaskInNonBlockModel(List<Runnable> tasks, ExecutorService threadPool) {
        if (Objects.isNull(threadPool)) {
            threadPool = DEFAULT_THREAD_POOL;
        }

        ExecutorService pool = threadPool;
        tasks.stream().filter(Objects::nonNull).forEach(task -> {
            try {
                pool.execute(task);
            } catch (Exception e) {
                log.info("Task execute failed:", e.fillInStackTrace());
                throw e;
            }
        });
    }

    public static void asyncRunSingleTask(Runnable task) {
        List<Runnable> tasks = Collections.singletonList(task);
        executeTaskInNonBlockModel(tasks, null);
    }

}
