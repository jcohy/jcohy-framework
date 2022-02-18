package com.jcohy.framework.commons.env;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:12:10
 * @since 2022.0.1
 */
public class Profiles {

	private Profiles() {
	}

	/**
	 * 开发环境.
	 */
	public static final String DEV_CODE = "dev";

	/**
	 * 测试环境.
	 */
	public static final String TEST_CODE = "test";

	/**
	 * 演示环境.
	 */
	public static final String DEMO_CODE = "demo";

	/**
	 * 预发布环境.
	 */
	public static final String STAG_CODE = "stag";

	/**
	 * 生产环境.
	 */
	public static final String PROD_CODE = "prod";

}
