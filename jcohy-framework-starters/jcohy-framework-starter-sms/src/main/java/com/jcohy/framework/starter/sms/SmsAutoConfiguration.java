package com.jcohy.framework.starter.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;

import com.jcohy.framework.starter.sms.ali.AliSmsTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.props.SmsProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:38
 * @since 2022.0.1
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    private final RedisUtils<String, String> redisUtils;

    public SmsAutoConfiguration(RedisUtils<String, String> redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 阿里的配置文件 {@link SmsProperties}.
     * @param smsProperties smsProperties
     * @return aliSmsTemplate
     */
    @Bean
    @ConditionalOnProperty(name = "jcohy.sms.name", havingValue = "ali")
    public AliSmsTemplate aliSmsTemplate(SmsProperties smsProperties) throws Exception {
        Config config = new Config()
                .setAccessKeyId(smsProperties.getAccessKey())
                .setAccessKeySecret(smsProperties.getSecretKey())
                .setRegionId(smsProperties.getRegionId());
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = new Client(config);
//        IAcsClient client = new DefaultAcsClient(profile);
        return new AliSmsTemplate(smsProperties, client, this.redisUtils);
    }

//    /**
//     * 腾讯的配置文件 {@link SmsProperties}.
//     * @param smsProperties smsProperties
//     * @return tencentSmsTemplate
//     */
//    @Bean
//    @ConditionalOnProperty(name = "jcohy.sms.name", havingValue = "tencent")
//    public TencentSmsTemplate tencentSmsTemplate(SmsProperties smsProperties) {
//        HttpProfile httpProfile = new HttpProfile();
//        httpProfile.setEndpoint(smsProperties.getEndpoint());
//        ClientProfile clientProfile = new ClientProfile();
//        clientProfile.setHttpProfile(httpProfile);
//        Credential cred = new Credential(smsProperties.getAccessKey(), smsProperties.getSecretKey());
//        SmsClient client = new SmsClient(cred, smsProperties.getRegionId(), clientProfile);
//        return new TencentSmsTemplate(smsProperties, client, this.redisUtils);
//    }

}
