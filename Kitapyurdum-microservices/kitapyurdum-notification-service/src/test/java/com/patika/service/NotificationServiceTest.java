package com.patika.service;

import com.patika.dto.enums.NotificationType;
import com.patika.model.Notification;
import com.patika.repository.NotificationRepository;
import com.patika.strategy.NotificationStrategy;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstancioExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationStrategy emailNotificationStrategy;

    @Mock
    private NotificationStrategy smsNotificationStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<NotificationType, NotificationStrategy> strategies = new HashMap<>();
        strategies.put(NotificationType.EMAIL, emailNotificationStrategy);
        strategies.put(NotificationType.SMS, smsNotificationStrategy);
        notificationService = new NotificationService(strategies, notificationRepository);
    }

    @Test
    void sendNotification_Success() {
        doNothing().when(emailNotificationStrategy).sendNotification(anyString());

        notificationService.sendNotification(NotificationType.EMAIL, "Test message");

        verify(emailNotificationStrategy, times(1)).sendNotification("Test message");
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void sendNotification_Failure() {
        doThrow(new RuntimeException("Failed to send email")).when(emailNotificationStrategy).sendNotification(anyString());

        notificationService.sendNotification(NotificationType.EMAIL, "Test message");

        verify(emailNotificationStrategy, times(1)).sendNotification("Test message");
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void getFailedNotifications() {
        List<Notification> failedNotifications = Instancio.ofList(Notification.class).size(3).create();
        when(notificationRepository.findByStatus("FAILED")).thenReturn(failedNotifications);

        List<Notification> result = notificationService.getFailedNotifications();

        assertEquals(failedNotifications.size(), result.size());
        verify(notificationRepository, times(1)).findByStatus("FAILED");
    }

    @Test
    void retryFailedNotifications() {
        List<Notification> failedNotifications = Instancio.ofList(Notification.class).size(3).create();
        when(notificationRepository.findByStatus("FAILED")).thenReturn(failedNotifications);

        notificationService.retryFailedNotifications();

        verify(notificationRepository, times(1)).findByStatus("FAILED");
        verify(notificationRepository, times(failedNotifications.size())).save(any(Notification.class));
    }
}
