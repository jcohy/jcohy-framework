package com.jcohy.framework.launch;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/4/22:17:48
 * @since 2022.0.1
 */
@SpringBootApplication
public class SimpleApplicationTest {

	public static void main(String[] args) {
		JcohyApplication.run("jcohy",SimpleApplicationTest.class,"");
	}
}
