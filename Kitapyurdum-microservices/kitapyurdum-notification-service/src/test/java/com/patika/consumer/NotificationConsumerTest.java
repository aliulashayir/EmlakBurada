package com.patika.consumer;

import com.patika.dto.NotificationDto;
import com.patika.dto.enums.NotificationType;
import com.patika.service.NotificationService;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.instancio.Select.field;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(InstancioExtension.class)
class NotificationConsumerTest {

    @InjectMocks
    private NotificationConsumer notificationConsumer;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotification() {
        NotificationDto notificationDto = Instancio.of(NotificationDto.class)
                .set(field(NotificationDto::getNotificationType), NotificationType.EMAIL)
                .set(field(NotificationDto::getMessage), "Test message")
                .create();

        notificationConsumer.sendNotification(notificationDto);

        verify(notificationService, times(1)).sendNotification(NotificationType.EMAIL, "Test message");
    }
}
