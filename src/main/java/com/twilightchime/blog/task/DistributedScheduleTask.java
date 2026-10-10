package com.twilightchime.blog.task;

import com.twilightchime.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedScheduleTask {
    private final StringRedisTemplate redis;
    private final BlogService blogService;

    public void cleanExpireBlogs() {
        String lockKey = "schedule:cleanExpireBlogs";
        String lockValue = UUID.randomUUID().toString();

        //redis 分布式下多个实例的锁
        Boolean locked = redis.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofMinutes(30));
        if(Boolean.FALSE.equals(locked)) {
            log.debug("未获取到锁, 跳过执行");
            return;
        }

        try {
            log.info("获取到锁, 开始执行任务");
        } finally {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end)";
            redis.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(lockKey), lockValue);
        }
    }
}
