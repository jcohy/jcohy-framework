package com.jcohy.framework.starter.oss.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.starter.oss.tencent.TencentOssTemplate;
import com.jcohy.framework.utils.constant.NumberConstant;


/**
 * 描述: .
 *
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:26
 * @since 2022.0.1
 */
@ConditionalOnProperty(name = "jcohy.oss.name", havingValue = "tencent")
public class TencentOssAutoConfiguration {

	private final OssProperties ossProperties;

	public TencentOssAutoConfiguration(OssProperties ossProperties) {
		this.ossProperties = ossProperties;
	}

	@Bean
	@ConditionalOnMissingBean(COSClient.class)
	public COSClient cosClient() {
		// 初始化用户身份信息（secretId, secretKey）
		COSCredentials credentials = new BasicCOSCredentials(this.ossProperties.getAccessKey(),
				this.ossProperties.getSecretKey());
		// 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		Region region = new Region(this.ossProperties.getEndpoint());
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java
		// SDK 部分。
		ClientConfig clientConfig = new ClientConfig(region);
		// 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
		clientConfig.setMaxConnectionsCount(1024);
		// 设置Socket层传输数据的超时时间，默认为50000毫秒。
		// clientConfig.setSocketTimeout(50000);
		// 设置建立连接的超时时间，默认为50000毫秒。
		// clientConfig.setConnectionTimeout(50000);
		// 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
		clientConfig.setConnectionRequestTimeout(NumberConstant.THOUSAND);
		return new COSClient(credentials, clientConfig);
	}

	@Bean
	@ConditionalOnMissingBean(TencentOssTemplate.class)
	@ConditionalOnBean({ COSClient.class, OssRules.class })
	public TencentOssTemplate tencentOssTemplate(COSClient cosClient, OssRules ossRules) {
		return new TencentOssTemplate(this.ossProperties, cosClient, ossRules);
	}

}
