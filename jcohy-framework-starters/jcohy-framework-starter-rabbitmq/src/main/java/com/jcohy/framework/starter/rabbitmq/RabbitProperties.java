package com.jcohy.framework.starter.rabbitmq;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/31/22:21:57
 * @since 2022.0.1
 */
@ConfigurationProperties(prefix = "jcohy.rabbit", ignoreInvalidFields = true)
public class RabbitProperties {

    List<Rabbit> rabbits = new LinkedList<>();

    public List<Rabbit> getRabbits() {
        return this.rabbits;
    }

    public void setRabbits(List<Rabbit> rabbits) {
        this.rabbits = rabbits;
    }

    public static class Rabbit {

        String queueName;

        String exchange;

        String routingKey;

        boolean durable = true;

        public String getQueueName() {
            return this.queueName;
        }

        public void setQueueName(String queueName) {
            this.queueName = queueName;
        }

        public String getExchange() {
            return this.exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return this.routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public boolean isDurable() {
            return this.durable;
        }

        public void setDurable(boolean durable) {
            this.durable = durable;
        }

    }

}
