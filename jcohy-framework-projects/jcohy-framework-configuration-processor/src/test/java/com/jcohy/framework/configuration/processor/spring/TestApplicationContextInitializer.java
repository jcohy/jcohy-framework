package com.jcohy.framework.configuration.processor.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.jcohy.framework.configuration.processor.annotations.JcohyApplicationContextInitializer;
import com.jcohy.framework.configuration.processor.annotations.JcohySpiService;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/15/22:10:46
 * @since 2022.0.1
 */
@JcohyApplicationContextInitializer
@JcohySpiService(TestApplicationContextInitializer.class)
public class TestApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("ApplicationContextInitializer......");
    }

}
