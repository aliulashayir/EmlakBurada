package com.patika.strategy;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@ExtendWith(InstancioExtension.class)
class PushNotificationStrategyTest {

    @InjectMocks
    private PushNotificationStrategy pushNotificationStrategy;

    @Test
    void sendNotification() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(pushNotificationStrategy).sendNotification(anyString());

        pushNotificationStrategy.sendNotification("Test message");

        verify(pushNotificationStrategy, times(1)).sendNotification("Test message");
    }
}
