package com.jcohy.framework.starter.redis.limter;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import com.jcohy.framework.utils.constant.CharPools;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:24
 * @since 2022.0.1
 */
public class RedisRateLimiterClient implements RateLimiterClient {

    /**
     * redis 限流 key 前缀.
     */
    private static final String REDIS_KEY_PREFIX = "limiter:";

    /**
     * 失败的默认返回值.
     */
    private static final long FAIL_CODE = 0;

    /**
     * redisTemplate.
     */
    private final StringRedisTemplate redisTemplate;

    /**
     * redisScript.
     */
    private final RedisScript<List<Long>> script;

    /**
     * env.
     */
    private final Environment environment;

    public RedisRateLimiterClient(StringRedisTemplate redisTemplate, RedisScript<List<Long>> script,
            Environment environment) {
        this.redisTemplate = redisTemplate;
        this.script = script;
        this.environment = environment;
    }

    @Override
    public boolean isAllowed(String key, long max, long ttl, TimeUnit timeUnit) {
        // redis key
        String redisKeyBuilder = REDIS_KEY_PREFIX + getApplicationName(this.environment) + CharPools.COLON + key;
        List<String> keys = Collections.singletonList(redisKeyBuilder);
        // 毫秒，考虑主从策略和脚本回放机制，这个time由客户端获取传入
        long now = System.currentTimeMillis();
        // 转为毫秒，pexpire
        long ttlMillis = timeUnit.toMillis(ttl);
        // 执行命令
        List<Long> results = this.redisTemplate.execute(this.script, keys, max + "", ttlMillis + "", now + "");
        // 结果为空返回失败
        if (results == null || results.isEmpty()) {
            return false;
        }
        // 判断返回成功
        Long result = results.get(0);
        return result != FAIL_CODE;
    }

    private static String getApplicationName(Environment environment) {
        return environment.getProperty("spring.application.name", "");
    }

}
