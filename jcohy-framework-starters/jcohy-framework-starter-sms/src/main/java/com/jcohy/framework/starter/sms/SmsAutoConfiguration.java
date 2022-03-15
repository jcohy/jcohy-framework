package com.jcohy.framework.starter.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.ali.AliSmsTemplate;
import com.jcohy.framework.starter.sms.props.SmsProperties;
import com.jcohy.framework.starter.sms.tencent.TencentSmsTemplate;

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
    public AliSmsTemplate aliSmsTemplate(SmsProperties smsProperties) {
        DefaultProfile profile;
        profile = DefaultProfile.getProfile(
                // API 支持的地域 ID，如短信 API 的值为：cn-hangzhou。
                smsProperties.getRegionId(),
                // 您的 AccessKey ID。
                smsProperties.getAccessKey(),
                // 您的 AccessKey Secret。
                smsProperties.getSecretKey());
        IAcsClient client = new DefaultAcsClient(profile);
        return new AliSmsTemplate(smsProperties, client, this.redisUtils);
    }

    /**
     * 腾讯的配置文件 {@link SmsProperties}.
     * @param smsProperties smsProperties
     * @return tencentSmsTemplate
     */
    @Bean
    @ConditionalOnProperty(name = "jcohy.sms.name", havingValue = "tencent")
    public TencentSmsTemplate tencentSmsTemplate(SmsProperties smsProperties) {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(smsProperties.getEndpoint());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        Credential cred = new Credential(smsProperties.getAccessKey(), smsProperties.getSecretKey());
        SmsClient client = new SmsClient(cred, smsProperties.getRegionId(), clientProfile);
        return new TencentSmsTemplate(smsProperties, client, this.redisUtils);
    }

}
