package com.patika.service;

import com.patika.dto.enums.NotificationType;
import com.patika.model.Notification;
import com.patika.repository.NotificationRepository;
import com.patika.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Map<NotificationType, NotificationStrategy> notificationStrategies;
    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationType type, String message) {
        Notification notification = new Notification();
        notification.setNotificationType(type);
        notification.setMessage(message);

        try {
            NotificationStrategy strategy = notificationStrategies.get(type);
            if (strategy != null) {
                strategy.sendNotification(message);
                notification.setStatus("SUCCESS");
            } else {
                throw new IllegalArgumentException("Unknown notification type: " + type);
            }
        } catch (Exception e) {
            notification.setStatus("FAILED");
        } finally {
            notificationRepository.save(notification);
        }
    }

    public List<Notification> getFailedNotifications() {
        return notificationRepository.findByStatus("FAILED");
    }

    public void retryFailedNotifications() {
        List<Notification> failedNotifications = getFailedNotifications();
        for (Notification notification : failedNotifications) {
            sendNotification(notification.getNotificationType(), notification.getMessage());
        }
    }
}
