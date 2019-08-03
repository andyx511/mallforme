package com.example.mall.service.impl;

import com.example.mall.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Alex
 * @Date: 2019/8/2 22:46
 * @Description:
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key ,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

   @ Override
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key,expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key ,long data){
        return stringRedisTemplate.opsForValue().increment(key,data);
    }
}
