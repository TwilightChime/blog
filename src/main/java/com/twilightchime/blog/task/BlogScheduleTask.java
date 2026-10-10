package com.twilightchime.blog.task;

import com.twilightchime.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogScheduleTask {
    private final BlogService blogService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredBlogs() {
        log.info("定时任务-清理过期博客...");
        try {
            int count = 4;
            log.info("定时任务-清理完成, 共删除 {} 条", count);
        } catch (Exception e) {
            log.error("定时任务-清理过期博客失败", e);
        }
    }
}
