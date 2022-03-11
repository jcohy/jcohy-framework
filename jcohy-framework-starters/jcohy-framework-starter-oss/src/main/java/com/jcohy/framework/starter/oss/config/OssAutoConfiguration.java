package com.jcohy.framework.starter.oss.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.jcohy.framework.starter.oss.DefaultOssRule;
import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.props.OssProperties;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:25
 * @since 2022.0.1
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "jcohy.oss.enable", havingValue = "true")
@Import({ AliOssAutoConfiguration.class, QiniuAutoConfiguration.class, TencentOssAutoConfiguration.class,
        MinioOssAutoConfiguration.class })
public class OssAutoConfiguration {

    private final OssProperties ossProperties;

    public OssAutoConfiguration(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Bean
    @ConditionalOnMissingBean(OssRules.class)
    public OssRules ossRule() {
        return new DefaultOssRule(this.ossProperties.getTenantMode());
    }

}
