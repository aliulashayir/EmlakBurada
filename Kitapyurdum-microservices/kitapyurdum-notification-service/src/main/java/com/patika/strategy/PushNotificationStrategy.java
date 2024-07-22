package com.patika.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PushNotificationStrategy implements NotificationStrategy {

    @Override
    public void sendNotification(String message) {
        // Push bildirim gönderim işlemi burada yapılacak
        log.info("Push notification sent: {}", message);
    }
}
