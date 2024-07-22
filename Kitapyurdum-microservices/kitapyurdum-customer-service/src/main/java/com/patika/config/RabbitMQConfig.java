package com.patika.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EMAIL_QUEUE = "send-notification-queue";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }
}
