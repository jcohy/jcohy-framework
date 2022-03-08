package com.jcohy.framework.launch.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.jcohy.framework.launch.props.SagaProperties;
import com.jcohy.framework.launch.props.YmlPropertySourcePostProcessor;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/8/22:14:13
 * @since 2022.0.1
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(SagaProperties.class)
public class SagaPropertyConfiguration {

    @Bean
    public YmlPropertySourcePostProcessor sagaPropertySourcePostProcessor() {
        return new YmlPropertySourcePostProcessor();
    }

}
