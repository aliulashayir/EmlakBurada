package com.patika.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String BILL_QUEUE = "bill-queue";

    @Bean
    public Queue billQueue() {
        return new Queue(BILL_QUEUE, true);
    }
}
