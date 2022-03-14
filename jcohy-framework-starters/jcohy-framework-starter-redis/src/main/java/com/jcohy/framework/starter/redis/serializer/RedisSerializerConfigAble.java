package com.jcohy.framework.starter.redis.serializer;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.jcohy.framework.starter.redis.props.RedisProperties;

/**
 * 描述: 序列化接口.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:12
 * @since 2022.0.1
 */
@SuppressWarnings("unchecked")
public interface RedisSerializerConfigAble {

    /**
     * 序列化接口.
     * @param properties 配置
     * @return redisSerializer
     */
    RedisSerializer<Object> redisSerializer(RedisProperties properties);

    /**
     * 默认的序列化方式.
     * @param properties 配置
     * @return redisSerializer
     */
    default RedisSerializer<Object> defaultRedisSerializer(RedisProperties properties) {
        RedisProperties.SerializerType serializerType = properties.getSerializerType();
        if (RedisProperties.SerializerType.JDK == serializerType) {
            /**
             * SpringBoot 扩展了 ClassLoader，进行分离打包的时候，使用到JdkSerializationRedisSerializer的地方
             * 会因为 ClassLoader 的不同导致加载不到 Class 指定使用项目的 ClassLoader
             *
             * JdkSerializationRedisSerializer
             * 默认使用{@link sun.misc.Launcher.AppClassLoader}
             * SpringBoot默认使用{@link org.springframework.boot.loader.LaunchedURLClassLoader}
             */
            ClassLoader classLoader = this.getClass().getClassLoader();
            return new JdkSerializationRedisSerializer(classLoader);
        }
        return new FastJson2JsonRedisSerializer(Object.class);
    }

}
