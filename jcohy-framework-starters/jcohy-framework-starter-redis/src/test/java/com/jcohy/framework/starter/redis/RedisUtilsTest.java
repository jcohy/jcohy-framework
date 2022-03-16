package com.jcohy.framework.starter.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/16/22:15:01
 * @since 2022.0.1
 */
@SpringBootTest(classes = TestApplication.class)
public class RedisUtilsTest {

    @Autowired
    RedisUtils<String, Object> sagaRedis;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @Test
    public void set() {
        this.sagaRedis.set("3", "8");
        String value = (String) this.redisTemplate.opsForValue().get("3");

        assertThat(value).isEqualTo("8");
        this.redisTemplate.delete("3");
    }
}