package com.jcohy.framework.starter.redis.serializer;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.jcohy.framework.starter.redis.RedisTemplateConfiguration;
import com.jcohy.framework.starter.redis.props.RedisProperties;
import com.jcohy.framework.starter.redis.props.RedisProperties.SerializerType;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:14
 * @since 2022.0.1
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisTemplateConfiguration.class)
@ConditionalOnClass(name = "io.protostuff.Schema")
public class ProtoStuffSerializerConfiguration implements RedisSerializerConfigAble {

    @Bean
    @ConditionalOnMissingBean
    @Override
    public RedisSerializer<Object> redisSerializer(RedisProperties properties) {
        if (SerializerType.PROTOSTUFF == properties.getSerializerType()) {
            return new ProtoStuffSerializer();
        }
        return defaultRedisSerializer(properties);
    }

}
