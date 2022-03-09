package com.jcohy.framework.starter.oss;

/**
 * 描述: .
 *
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:44
 * @since 2022.0.1
 */
public class DefaultOssRule implements OssRules {

	/**
	 * 租户模式.
	 */
	private final Boolean tenantMode;

	public DefaultOssRule(final Boolean tenantMode) {
		this.tenantMode = tenantMode;
	}

	@Override
	public String bucketRule(String bucket) {
		return defaultBucketRule(bucket);
	}

	@Override
	public String fileRule(String fileName) {
		return defaultFileRule(fileName);
	}

	public Boolean getTenantMode() {
		return this.tenantMode;
	}

}
