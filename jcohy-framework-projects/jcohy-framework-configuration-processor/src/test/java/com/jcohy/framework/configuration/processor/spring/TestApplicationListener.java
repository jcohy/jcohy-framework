package com.jcohy.framework.configuration.processor.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.jcohy.framework.configuration.processor.annotations.JcohyApplicationListener;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2/15/22:10:48
 * @since 2022.0.1
 */
@JcohyApplicationListener
public class TestApplicationListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationEvent......");
    }

}
