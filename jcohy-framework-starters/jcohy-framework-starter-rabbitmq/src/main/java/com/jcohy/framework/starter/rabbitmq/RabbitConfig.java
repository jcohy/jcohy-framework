package com.jcohy.framework.starter.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/31/22:21:56
 * @since 2022.0.1
 */
@Configuration
@AutoConfigureAfter(RabbitAutoConfiguration.class)
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfig implements SmartInitializingSingleton {

    private final RabbitProperties rabbitProperties;

    private final RabbitAdmin rabbitAdmin;

    public RabbitConfig(RabbitProperties rabbitProperties, RabbitAdmin rabbitAdmin) {
        this.rabbitProperties = rabbitProperties;
        this.rabbitAdmin = rabbitAdmin;
    }

    private void createQueue(RabbitProperties.Rabbit rabbit) {
        Queue queue = new Queue(rabbit.getQueueName(), rabbit.isDurable());
        DirectExchange directExchange = new DirectExchange(rabbit.getExchange());
        Binding binding = BindingBuilder.bind(queue).to(directExchange).with(rabbit.getRoutingKey());
        this.rabbitAdmin.declareQueue(queue);
        this.rabbitAdmin.declareExchange(directExchange);
        this.rabbitAdmin.declareBinding(binding);
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.rabbitProperties.getRabbits().forEach(this::createQueue);
    }

}
