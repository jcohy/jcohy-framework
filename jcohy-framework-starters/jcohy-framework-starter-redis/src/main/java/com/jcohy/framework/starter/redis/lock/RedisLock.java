package com.jcohy.framework.starter.redis.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 分布式锁注解，redisson，支持的锁的种类有很多，适合注解形式的只有重入锁、公平锁.
 * <ol>
 * <li>可重入锁（Reentrant Lock）</li>
 * <li>公平锁（Fair Lock）</li>
 * <li>联锁（MultiLock）</li>
 * <li>红锁（RedLock）</li>
 * <li>读写锁（ReadWriteLock）</li>
 * </ol>
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/14/22:16:56
 * @since 2022.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLock {

    /**
     * 分布式锁的 key，必须：请保持唯一性.
     * @return key
     */
    String value();

    /**
     * 分布式锁参数，可选，支持 spring el # 读取方法参数和 @ 读取 spring bean.
     * @return param
     */
    String param() default "";

    /**
     * 等待锁超时时间，默认 30.
     * @return int
     */
    long waitTime() default 30;

    /**
     * 自动解锁时间，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放，默认 100.
     * @return int
     */
    long leaseTime() default 100;

    /**
     * 时间单温，默认为秒.
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 默认公平锁.
     * @return LockType
     */
    LockType type() default LockType.FAIR;

}
