package com.gec.hawsteproject.hawaste.service.impl;

import com.anji.captcha.service.CaptchaCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {
    @Autowired
            //只有StringRedisTemplate才能正常使用incr
//    RedisService redisService;
    StringRedisTemplate redisTemplate;


    @Override
    public void set(String key, String value, long expiresInSeconds) {
        redisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String type() {
        return "redis";
    }

    @Override
    public Long increment(String key, long val) {
        return redisTemplate.opsForValue().increment(key,val);
    }
}
