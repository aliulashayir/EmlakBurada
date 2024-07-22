package com.patika.config;

import com.patika.dto.enums.NotificationType;
import com.patika.strategy.EmailNotificationStrategy;
import com.patika.strategy.NotificationStrategy;
import com.patika.strategy.PushNotificationStrategy;
import com.patika.strategy.SMSNotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StrategyConfig {

    private final List<NotificationStrategy> notificationStrategies;

    @Bean
    public Map<NotificationType, NotificationStrategy> sendNotificationByType() {
        Map<NotificationType, NotificationStrategy> messagesByType = new EnumMap<>(NotificationType.class);
        notificationStrategies.forEach(notificationStrategy -> {
            if (notificationStrategy instanceof EmailNotificationStrategy) {
                messagesByType.put(NotificationType.EMAIL, notificationStrategy);
            } else if (notificationStrategy instanceof SMSNotificationStrategy) {
                messagesByType.put(NotificationType.SMS, notificationStrategy);
            } else if (notificationStrategy instanceof PushNotificationStrategy) {
                messagesByType.put(NotificationType.PUSH_NOTIFICATION, notificationStrategy);
            }
        });
        return messagesByType;
    }
}
