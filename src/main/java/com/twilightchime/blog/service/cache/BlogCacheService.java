package com.twilightchime.blog.service.cache;

import com.twilightchime.blog.vo.BlogDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BlogCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "blog:detail:";
    private static final long CACHE_TTL = 30;

    public void setBlog(Long id, BlogDetailVO blog) {
        String key = CACHE_KEY_PREFIX + id;
        redisTemplate.opsForValue().set(key, blog, Duration.ofMinutes(CACHE_TTL));
    }
    
    public BlogDetailVO getBlog(Long id) {
        String key = CACHE_KEY_PREFIX + id;
        return (BlogDetailVO) redisTemplate.opsForValue().get(key);
    }
    
    public void deleteBlog(Long id) {
        redisTemplate.delete(CACHE_KEY_PREFIX + id);
    }

    public boolean existsBlog(Long id) {
        return redisTemplate.hasKey(CACHE_KEY_PREFIX + id);
    }

    public Long incrementViews(Long id) {
        String key = "blog:views:" + id;
        return redisTemplate.opsForValue().increment(key);
    }
}
