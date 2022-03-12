package com.jcohy.framework.starter.oss.config;

import io.minio.MinioClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.minio.MinioOssTemplate;
import com.jcohy.framework.starter.oss.props.OssProperties;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/9/22:16:25
 * @since 2022.0.1
 */
@ConditionalOnProperty(name = "jcohy.oss.name", havingValue = "minio")
public class MinioOssAutoConfiguration {

    private final OssProperties ossProperties;

    public MinioOssAutoConfiguration(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Bean
    @ConditionalOnMissingBean(MinioClient.class)
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(this.ossProperties.getEndpoint())
                .credentials(this.ossProperties.getAccessKey(), this.ossProperties.getSecretKey()).build();
    }

    @Bean
    @ConditionalOnBean({ MinioClient.class, OssRules.class })
    @ConditionalOnMissingBean(MinioOssTemplate.class)
    public MinioOssTemplate minioTemplate(MinioClient minioClient, OssRules ossRule) {
        return new MinioOssTemplate(minioClient, ossRule, this.ossProperties);
    }

}
