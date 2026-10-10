package com.twilightchime.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);   //线程池大小
        scheduler.setThreadNamePrefix("线程 ScheduleConfig-");    //线程名前缀
        scheduler.setErrorHandler(t -> log.error("定时任务异常", t)); //全局异常处理
        scheduler.setWaitForTasksToCompleteOnShutdown(true);    //优雅关闭
        scheduler.setAwaitTerminationSeconds(60);   //等待超时
        scheduler.initialize();
        taskRegistrar.setTaskScheduler(scheduler);
    }
}

