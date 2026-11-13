package com.twilightchime.blog.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        //key 使用String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //value 使用json序列化
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        // 1. 构造 ObjectMapper，支持 LocalDateTime
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 开启类型信息，反序列化时能还原成原始类型
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(om);

        // 2. 默认缓存配置：key 用 String，value 用 JSON
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))                 // 默认过期时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer))
                .computePrefixWith(cacheName -> "blog:cache:" + cacheName + "::") // 统一 key 前缀
                // 如果不想缓存 null，可以加：
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory).cacheDefaults(defaultConfig)
                // 针对不同 cacheName 可以单独设 TTL
                .withCacheConfiguration("blogDetail", defaultConfig.entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("blogList", defaultConfig.entryTtl(Duration.ofMinutes(5)))
                .build();
    }
}
