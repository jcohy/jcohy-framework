package com.jcohy.framework.starter.redis.support;

import java.util.concurrent.TimeUnit;

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
public class RateLimiterException extends RuntimeException {

    private final String key;

    private final long max;

    private final long ttl;

    private final TimeUnit timeUnit;

    public RateLimiterException(String key, long max, long ttl, TimeUnit timeUnit) {
        super(String.format("您的访问次数已超限：%s，速率：%d/%ds", key, max, timeUnit.toSeconds(ttl)));
        this.key = key;
        this.max = max;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
    }

    public String getKey() {
        return this.key;
    }

    public long getMax() {
        return this.max;
    }

    public long getTtl() {
        return this.ttl;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

}
