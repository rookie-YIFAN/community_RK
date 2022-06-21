package com.rookie.springboot_001;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.temporal.Temporal;

@SpringBootTest
public class AppTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("key","value");
        System.out.println(redisTemplate.opsForValue().get("key"));
        System.out.println(redisTemplate.opsForValue().get("name"));

    }
}
