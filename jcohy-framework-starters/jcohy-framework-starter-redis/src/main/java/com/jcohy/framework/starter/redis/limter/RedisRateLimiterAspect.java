package com.jcohy.framework.starter.redis.limter;

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
import org.springframework.lang.NonNull;
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
 * @version 2022.0.1 3/14/22:17:23
 * @since 2022.0.1
 */
@Aspect
public class RedisRateLimiterAspect implements ApplicationContextAware {

    /**
     * 表达式处理.
     */
    private final JcohyExpressionEvaluator evaluator = new JcohyExpressionEvaluator();

    /**
     * redis 限流服务.
     */
    private final RedisRateLimiterClient rateLimiterClient;

    private ApplicationContext applicationContext;

    public RedisRateLimiterAspect(RedisRateLimiterClient rateLimiterClient) {
        this.rateLimiterClient = rateLimiterClient;
    }

    @Around("@annotation(limiter)")
    public Object aroundRateLimiter(ProceedingJoinPoint point, RateLimiter limiter) throws Throwable {
        String limitKey = limiter.value();
        Assert.hasText(limitKey, "@RateLimiter value must have length; it must not be null or empty");
        // el 表达式
        String limitParam = limiter.param();
        // 表达式不为空
        String rateKey;
        if (StringUtils.isNotEmpty(limitParam)) {
            String evalAsText = evalLimitParam(point, limitParam);
            rateKey = limitKey + CharPools.COLON + evalAsText;
        }
        else {
            rateKey = limitKey;
        }
        long max = limiter.max();
        long ttl = limiter.ttl();
        TimeUnit timeUnit = limiter.timeUnit();
        return this.rateLimiterClient.allow(rateKey, max, ttl, timeUnit, point::proceed);
    }

    /**
     * 计算参数表达式.
     * @param point proceedingJoinPoint
     * @param limitParam limitParam
     * @return 结果
     */
    private String evalLimitParam(ProceedingJoinPoint point, String limitParam) {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Object[] args = point.getArgs();
        Object target = point.getTarget();
        Class<?> targetClass = target.getClass();
        EvaluationContext context = this.evaluator.createContext(method, args, target, targetClass,
                this.applicationContext);
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        return this.evaluator.evalAsText(limitParam, elementKey, context);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
