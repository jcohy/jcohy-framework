package com.jcohy.framework.starter.oss.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.ali.AliOssTemplate;
import com.jcohy.framework.starter.oss.props.BucketScope;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.utils.constant.NumberConstant;

/**
 * 描述: .
 *
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:24
 * @since 2022.0.1
 */
@ConditionalOnProperty(name = "jcohy.oss.name", havingValue = "ali")
public class AliOssAutoConfiguration {

	private final OssProperties ossProperties;

	public AliOssAutoConfiguration(OssProperties ossProperties) {
		this.ossProperties = ossProperties;
	}

	@Bean
	public OSSClient ossClient() {
		// 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
		ClientConfiguration conf = new ClientConfiguration();
		// 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
		conf.setMaxConnections(1024);
		// 设置Socket 层传输数据的超时时间，默认为 50000 毫秒。
		// conf.setSocketTimeout(50000);
		// 设置建立连接的超时时间，默认为50000毫秒。
		// conf.setConnectionTimeout(50000);
		// 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
		conf.setConnectionRequestTimeout(NumberConstant.THOUSAND);
		// 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
		// conf.setIdleConnectionTime(60000);
		// 设置失败请求重试次数，默认为3次。
		// conf.setMaxErrorRetry(5);
		CredentialsProvider credentialsProvider = new DefaultCredentialProvider(this.ossProperties.getAccessKey(),
				this.ossProperties.getSecretKey());
		OSSClient ossClient = new OSSClient(this.ossProperties.getEndpoint(), credentialsProvider, conf);
		ossClient.setBucketAcl(this.ossProperties.getBucket(),
				BucketScope.getAccessControl(this.ossProperties.getScope()));
		return ossClient;
	}

	@Bean
	@ConditionalOnMissingBean(AliOssTemplate.class)
	@ConditionalOnBean({ OSSClient.class, OssRules.class })
	public AliOssTemplate aliossTemplate(OSSClient ossClient, OssRules ossRules) {
		return new AliOssTemplate(ossClient, this.ossProperties, ossRules);
	}

}
