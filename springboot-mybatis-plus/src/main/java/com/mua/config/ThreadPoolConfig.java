package com.mua.config;

import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-03-21 下午 4:44
 * @Comment: 线程池配置
 */
@Setter
@EnableAsync
@Configuration
// @ConfigurationProperties(prefix = "task.pool")    //可在配置文件中配置
public class ThreadPoolConfig {

    /**
     * 线程池中的核心线程数量,默认为1
     */
    private int corePoolSize = 10;
    /**
     * 线程池中的最大线程数量
     */
    private int maxPoolSize = 10;
    /**
     * 线程池中允许线程的空闲时间,默认为 60s
     */
    private int keepAliveTime = ((int) TimeUnit.SECONDS.toSeconds(30));
    /**
     * 线程池中的队列最大数量
     */
    private int queueCapacity = 5000;

    /**
     * 线程的名称前缀
     */
    private static final String THREAD_PREFIX = "thread-call-runner-%d";

    @Bean(name = "asyncServiceExecutor")
    public ThreadPoolTaskExecutor threadPool() {
        //https://blog.csdn.net/inthat/article/details/109596279?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522167945588816800184126717%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=167945588816800184126717&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-2-109596279-null-null.142^v75^control_1,201^v4^add_ask,239^v2^insert_chatgpt&utm_term=ThreadPoolTaskExecutor%E5%92%8CThreadPoolExecutor&spm=1018.2226.3001.4187
        /**
         * ThreadPoolTaskExecutor和ThreadPoolExecutor有何区别：
         *  1、ThreadPoolTaskExecutor是对ThreadPoolExecutor进行了封装处理
         *  2、ThreadPoolTaskExecutor这个类则是spring包下的，是sring为我们提供的线程池类
         */
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(THREAD_PREFIX);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();

        return executor;
    }
}
