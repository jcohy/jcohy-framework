package com.jcohy.framework.launch;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/4/22:17:32
 * @since 2022.0.1
 */
@SpringBootTest(classes = SimpleApplicationTest.class,webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestApplication {

	@Test
	void contestLoads() {

	}

}
