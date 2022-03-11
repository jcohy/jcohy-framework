package com.jcohy.framework.starter.oss.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.starter.oss.qiniu.QiniuOssTemplate;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:26
 * @since 2022.0.1
 */
@ConditionalOnProperty(name = "jcohy.oss.name", havingValue = "qiniu")
public class QiniuAutoConfiguration {

    private final OssProperties ossProperties;

    public QiniuAutoConfiguration(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Bean
    @ConditionalOnMissingBean(com.qiniu.storage.Configuration.class)
    public com.qiniu.storage.Configuration qnConfiguration() {
        return new com.qiniu.storage.Configuration(Zone.autoZone());
    }

    @Bean
    public Auth auth() {
        return Auth.create(this.ossProperties.getAccessKey(), this.ossProperties.getSecretKey());
    }

    @Bean
    @ConditionalOnBean(com.qiniu.storage.Configuration.class)
    public UploadManager uploadManager(com.qiniu.storage.Configuration cfg) {
        return new UploadManager(cfg);
    }

    @Bean
    @ConditionalOnBean(com.qiniu.storage.Configuration.class)
    public BucketManager bucketManager(com.qiniu.storage.Configuration cfg, Auth auth) {
        return new BucketManager(auth, cfg);
    }

    @Bean
    @ConditionalOnMissingBean(QiniuOssTemplate.class)
    @ConditionalOnBean({ Auth.class, UploadManager.class, BucketManager.class, OssRules.class })
    public QiniuOssTemplate qiniuTemplate(Auth auth, UploadManager uploadManager, BucketManager bucketManager,
            OssRules ossRule) {
        return new QiniuOssTemplate(auth, uploadManager, bucketManager, this.ossProperties, ossRule);
    }

}
