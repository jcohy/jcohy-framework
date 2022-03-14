package com.jcohy.framework.starter.redis.limter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 分布式 限流注解，默认速率为 500/ms.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/14/22:17:21
 * @since 2022.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {

    /**
     * 限流的 key 支持，必须：请保持唯一性.
     * @return key
     */
    String value();

    /**
     * 限流的参数，可选，支持 spring el # 读取方法参数和 @ 读取 spring bean.
     * @return param
     */
    String param() default "";

    /**
     * 支持的最大请求，默认: 100.
     * @return 请求数
     */
    long max() default 100L;

    /**
     * 持续时间，默认: 3600.
     * @return 持续时间
     */
    long ttl() default 1L;

    /**
     * 时间单位，默认为分.
     * @return TimeUnit
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

}
