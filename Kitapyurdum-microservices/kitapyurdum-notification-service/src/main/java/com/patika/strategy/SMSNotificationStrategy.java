package com.patika.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SMSNotificationStrategy implements NotificationStrategy {

    @Override
    public void sendNotification(String message) {
        // SMS gönderim işlemi burada yapılacak
        log.info("SMS sent: {}", message);
    }
}
