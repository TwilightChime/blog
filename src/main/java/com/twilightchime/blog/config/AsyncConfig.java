package com.twilightchime.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   //核心线程数
        executor.setMaxPoolSize(50);    //最大线程数
        executor.setQueueCapacity(200); //队伍容量
        executor.setKeepAliveSeconds(60);   //空闲线程存活时间
        executor.setThreadNamePrefix("线程 Async-"); //线程名前缀(便于排查)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();
        return executor;
    }

    // 传递 TraceId 到异步线程
    public AsyncUncaughtExceptionHandler getUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("异步任务异常: method={}, params={}", method.getName(), params, ex);
        };
    }
}

