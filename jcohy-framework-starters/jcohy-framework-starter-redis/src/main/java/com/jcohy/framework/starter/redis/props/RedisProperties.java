package com.jcohy.framework.starter.redis.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:16:51
 * @since 2022.0.1
 */
@ConfigurationProperties("jcohy.redis")
public class RedisProperties {

    /**
     * 序列化方式.
     */
    private SerializerType serializerType = SerializerType.JSON;

    /**
     * 分布式锁配置.
     */
    private Locks locks;

    public Locks getLock() {
        return this.locks;
    }

    public void setLock(Locks locks) {
        this.locks = locks;
    }

    public enum SerializerType {

        /**
         * 默认:ProtoStuff 序列化.
         */
        PROTOSTUFF,
        /**
         * json 序列化.
         */
        JSON,
        /**
         * jdk 序列化.
         */
        JDK

    }

    public SerializerType getSerializerType() {
        return this.serializerType;
    }

    public void setSerializerType(SerializerType serializerType) {
        this.serializerType = serializerType;
    }

}
