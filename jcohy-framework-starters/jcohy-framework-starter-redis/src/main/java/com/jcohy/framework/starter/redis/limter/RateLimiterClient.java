package com.jcohy.framework.starter.redis.limter;

import java.util.concurrent.TimeUnit;

import com.jcohy.framework.starter.redis.support.RateLimiterException;
import com.jcohy.framework.utils.exceptions.CheckedSupplier;
import com.jcohy.framework.utils.exceptions.Exceptions;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:22
 * @since 2022.0.1
 */
public interface RateLimiterClient {

    /**
     * 服务是否被限流.
     * @param key 自定义的key，请保证唯一
     * @param max 支持的最大请求
     * @param ttl 时间,单位默认为秒（seconds）
     * @return 是否允许
     */
    default boolean isAllowed(String key, long max, long ttl) {
        return this.isAllowed(key, max, ttl, TimeUnit.SECONDS);
    }

    /**
     * 服务是否被限流.
     * @param key 自定义的key，请保证唯一
     * @param max 支持的最大请求
     * @param ttl 时间
     * @param timeUnit 时间单位
     * @return 是否允许
     */
    boolean isAllowed(String key, long max, long ttl, TimeUnit timeUnit);

    /**
     * 服务限流，被限制时抛出 RateLimiterException 异常，需要自行处理异常.
     * @param key 自定义的key，请保证唯一
     * @param max 支持的最大请求
     * @param ttl 时间
     * @param supplier supplier 函数式
     * @param <T> t
     * @return 函数执行结果
     */
    default <T> T allow(String key, long max, long ttl, CheckedSupplier<T> supplier) {
        return allow(key, max, ttl, TimeUnit.SECONDS, supplier);
    }

    /**
     * 服务限流，被限制时抛出 RateLimiterException 异常，需要自行处理异常.
     * @param key 自定义的 key，请保证唯一
     * @param max 支持的最大请求
     * @param ttl 时间
     * @param timeUnit 时间单位
     * @param supplier supplier 函数式
     * @param <T> t
     * @return 函数执行结果
     */
    default <T> T allow(String key, long max, long ttl, TimeUnit timeUnit, CheckedSupplier<T> supplier) {
        boolean isAllowed = this.isAllowed(key, max, ttl, timeUnit);
        if (isAllowed) {
            try {
                return supplier.get();
            }
            catch (Throwable ex) {
                throw Exceptions.unchecked(ex);
            }
        }
        throw new RateLimiterException(key, max, ttl, timeUnit);
    }

}
