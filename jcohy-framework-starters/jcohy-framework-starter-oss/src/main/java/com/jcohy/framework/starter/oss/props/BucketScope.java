package com.jcohy.framework.starter.oss.props;

import com.aliyun.oss.model.CannedAccessControlList;

/**
 * 描述: .
 *
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:11:45
 * @since 2022.0.1
 */
public enum BucketScope {

	/**
	 * 私有.
	 */
	PRIVATE("private"),

	/**
	 * 公共读.
	 */
	READ("read"),

	/**
	 * 公共读.
	 */
	WRITE("write"),

	/**
	 * 公共读写.
	 */
	READ_WRITE("public-read-write");

	private final String scope;

	BucketScope(String scope) {
		this.scope = scope;
	}

	public String getScope() {
		return this.scope;
	}

	public static CannedAccessControlList getAccessControl(BucketScope scope) {
		switch (scope) {
			case READ:
				return CannedAccessControlList.PublicRead;
			case READ_WRITE:
				return CannedAccessControlList.PublicReadWrite;
			default:
				return CannedAccessControlList.Private;
		}
	}

	public static com.qcloud.cos.model.CannedAccessControlList getTencentAccessControl(BucketScope scope) {
		switch (scope) {
			case READ:
				return com.qcloud.cos.model.CannedAccessControlList.PublicRead;
			case READ_WRITE:
				return com.qcloud.cos.model.CannedAccessControlList.PublicReadWrite;
			default:
				return com.qcloud.cos.model.CannedAccessControlList.Private;
		}
	}

}
