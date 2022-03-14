package com.jcohy.framework.starter.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcohy.framework.starter.redis.props.Locks;
import com.jcohy.framework.starter.redis.props.Locks.Mode;
import com.jcohy.framework.starter.redis.props.RedisProperties;
import com.jcohy.framework.utils.StringUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:07
 * @since 2022.0.1
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedissonClient.class)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(value = "jcohy.redis.locks.enabled", havingValue = "true")
public class RedisLockAutoConfiguration {

    private static Config singleConfig(RedisProperties properties) {
        Config config = new Config();
        SingleServerConfig serversConfig = config.useSingleServer();
        Locks sagaRedisLock = properties.getLock();
        serversConfig.setAddress(sagaRedisLock.getAddress());
        String password = sagaRedisLock.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(sagaRedisLock.getDatabase());
        serversConfig.setConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setIdleConnectionTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setConnectTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setTimeout(sagaRedisLock.getTimeout());
        return config;
    }

    private static Config masterSlaveConfig(RedisProperties properties) {
        Config config = new Config();
        Locks sagaRedisLock = properties.getLock();
        MasterSlaveServersConfig serversConfig = config.useMasterSlaveServers();
        serversConfig.setMasterAddress(sagaRedisLock.getMasterAddress());
        serversConfig.addSlaveAddress(sagaRedisLock.getSlaveAddress());
        String password = sagaRedisLock.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(sagaRedisLock.getDatabase());
        serversConfig.setMasterConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setIdleConnectionTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setConnectTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setTimeout(sagaRedisLock.getTimeout());
        return config;
    }

    private static Config sentinelConfig(RedisProperties properties) {
        Config config = new Config();
        Locks sagaRedisLock = properties.getLock();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setMasterName(sagaRedisLock.getMasterName());
        serversConfig.addSentinelAddress(sagaRedisLock.getSentinelAddress());
        String password = sagaRedisLock.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(sagaRedisLock.getDatabase());
        serversConfig.setMasterConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setIdleConnectionTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setConnectTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setTimeout(sagaRedisLock.getTimeout());
        return config;
    }

    private static Config clusterConfig(RedisProperties properties) {
        Config config = new Config();
        Locks sagaRedisLock = properties.getLock();
        ClusterServersConfig serversConfig = config.useClusterServers();
        serversConfig.addNodeAddress(sagaRedisLock.getNodeAddress());
        String password = sagaRedisLock.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setMasterConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(sagaRedisLock.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(sagaRedisLock.getIdleSize());
        serversConfig.setIdleConnectionTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setConnectTimeout(sagaRedisLock.getConnectionTimeout());
        serversConfig.setTimeout(sagaRedisLock.getTimeout());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockClient redisLockClient(RedisProperties properties) {
        return new RedisLockClientImpl(redissonClient(properties));
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockAspect redisLockAspect(RedisLockClient redisLockClient) {
        return new RedisLockAspect(redisLockClient);
    }

    private static RedissonClient redissonClient(RedisProperties properties) {
        Mode mode = properties.getLock().getMode();
        Config config;
        switch (mode) {
        case SENTINEL:
            config = sentinelConfig(properties);
            break;
        case CLUSTER:
            config = clusterConfig(properties);
            break;
        case MASTER:
            config = masterSlaveConfig(properties);
            break;
        case SINGLE:
            config = singleConfig(properties);
            break;
        default:
            config = new Config();
            break;
        }
        return Redisson.create(config);
    }

}
