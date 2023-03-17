package com.huytd.basecacheredis.utils;

import com.huytd.basecacheredis.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserCacheUtils {
    @Value("${redis.prefix-user}")
    private String USER_CACHE_PREFIX;
    @Value("${redis.time-cache-seconds}")
    private long timeCache;
    private final RedisTemplate<String, Object> redisTemplate;

    private long timeCacheRedis() {
        Random random = new Random();
        //5 minute + random timeCacheRedis
        return timeCache * 1000000000 + random.nextInt(1000000000) + 1;
    }

    public void saveUserToCache(User user) {
        String key = USER_CACHE_PREFIX + user.getId();
        redisTemplate.opsForValue().set(key, user, timeCacheRedis(), TimeUnit.NANOSECONDS);
    }

    public User getUserFromCache(Long userId) {
        String key = USER_CACHE_PREFIX + userId;
        return (User) redisTemplate.opsForValue().get(key);
    }
}
