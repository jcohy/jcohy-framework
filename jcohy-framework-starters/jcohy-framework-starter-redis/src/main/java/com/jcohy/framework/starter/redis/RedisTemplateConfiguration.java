package com.jcohy.framework.starter.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.jcohy.framework.starter.redis.props.RedisProperties;
import com.jcohy.framework.starter.redis.serializer.RedisSerializerConfigAble;
import com.jcohy.framework.starter.redis.serializer.StringRedisSerializer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @param <T> value 范型类型
 * @author jiac
 * @version 2022.0.1 3/14/22:17:14
 * @since 2022.0.1
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisTemplateConfiguration<T> implements RedisSerializerConfigAble {

    private static final Logger logger = LoggerFactory.getLogger(RedisTemplateConfiguration.class);

    /**
     * value 值 序列化.
     * @return redisSerializer
     */
    @Bean
    @ConditionalOnMissingBean(RedisSerializer.class)
    @Override
    public RedisSerializer<Object> redisSerializer(RedisProperties properties) {
        return defaultRedisSerializer(properties);
    }

    @SuppressWarnings("all")
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory,
            RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        // 序列化
        // key 的序列化采用 StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // value 值的序列化采用 redisSerializer
        template.setValueSerializer(redisSerializer);
        template.setHashValueSerializer(redisSerializer);

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

//    @ConditionalOnMissingBean(name = "redisUtils")
    @Bean
    public RedisUtils<String, T> redisUtils(RedisTemplate<String, T> redisTemplate) {
        return new RedisUtils<>(redisTemplate);
    }

}
