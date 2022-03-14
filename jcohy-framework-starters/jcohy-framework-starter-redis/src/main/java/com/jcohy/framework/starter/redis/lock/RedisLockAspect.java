package com.jcohy.framework.starter.redis.lock;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.Assert;

import com.jcohy.framework.starter.redis.support.JcohyExpressionEvaluator;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.CharPools;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:04
 * @since 2022.0.1
 */
@Aspect
public class RedisLockAspect implements ApplicationContextAware {

    /**
     * 表达式处理.
     */
    private static final JcohyExpressionEvaluator EVALUATOR = new JcohyExpressionEvaluator();

    /**
     * redis 限流服务.
     */
    private final RedisLockClient redisLockClient;

    private ApplicationContext applicationContext;

    public RedisLockAspect(RedisLockClient redisLockClient) {
        this.redisLockClient = redisLockClient;
    }

    /**
     * AOP 环切 注解 @RedisLock.
     * @param point point
     * @param redisLock redisLock
     * @return /
     */
    @Around("@annotation(redisLock)")
    public Object aroundRedisLock(ProceedingJoinPoint point, RedisLock redisLock) {
        String lockName = redisLock.value();
        Assert.hasText(lockName, "@RedisLock value must have length; it must not be null or empty");
        // el 表达式
        String lockParam = redisLock.param();
        // 表达式不为空
        String lockKey;
        if (StringUtils.isNotEmpty(lockParam)) {
            String evalAsText = evalLockParam(point, lockParam);
            lockKey = lockName + CharPools.COLON + evalAsText;
        }
        else {
            lockKey = lockName;
        }
        LockType lockType = redisLock.type();
        long waitTime = redisLock.waitTime();
        long leaseTime = redisLock.leaseTime();
        TimeUnit timeUnit = redisLock.timeUnit();
        return this.redisLockClient.lock(lockKey, lockType, waitTime, leaseTime, timeUnit, point::proceed);
    }

    /**
     * 计算参数表达式.
     * @param point proceedingJoinPoint
     * @param lockParam lockParam
     * @return 结果
     */
    private String evalLockParam(ProceedingJoinPoint point, String lockParam) {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Object[] args = point.getArgs();
        Object target = point.getTarget();
        Class<?> targetClass = target.getClass();
        EvaluationContext context = EVALUATOR.createContext(method, args, target, targetClass, this.applicationContext);
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        return EVALUATOR.evalAsText(lockParam, elementKey, context);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
