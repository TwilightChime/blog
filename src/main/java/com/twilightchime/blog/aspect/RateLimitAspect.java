package com.twilightchime.blog.aspect;

import com.twilightchime.blog.exception.BusinessException;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitAspect {
    private final StringRedisTemplate redisTemplate;

    private static final String LUA_SCRIPT = """
            local key = KEYS[1]
            local count = tonumber(ARGV[1])
            local time = tonumber(ARGV[2])
            local current = redis.call('get', key)
            if current and tonumber(current) >= count then
                return 0
            end
            redis.call('incr', key)
            if not current then
                redis.call('expire', key, time)
            end
            return 1
            """;

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint pjp, @Nonnull RateLimit rateLimit) throws Throwable {

        try {
            String key = rateLimit.key().isEmpty() ? pjp.getSignature().toShortString() : rateLimit.key();
            String redisKey = "rate_limit:" + key;

//            boolean allowed = checkLimit(key, rateLimit);
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(redisKey), String.valueOf(rateLimit.count()), String.valueOf(rateLimit.time()));
            if (result == 0) {
                throw new BusinessException("请求过于频繁, 请稍后重试");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            // ✅ 关键：Redis 故障等系统异常，降级放行，绝不影响业务
            log.error("限流切面异常，已降级放行请求: key={}, error={}",
                    rateLimit.key(), e.getMessage());
            // 不再抛出异常，继续往下执行
        }
        return pjp.proceed();
    }
}
