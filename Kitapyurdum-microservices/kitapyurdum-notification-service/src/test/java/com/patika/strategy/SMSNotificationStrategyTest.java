package com.patika.strategy;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@ExtendWith(InstancioExtension.class)
class SMSNotificationStrategyTest {

    @InjectMocks
    private SMSNotificationStrategy smsNotificationStrategy;

    @Test
    void sendNotification() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(smsNotificationStrategy).sendNotification(anyString());

        smsNotificationStrategy.sendNotification("Test message");

        verify(smsNotificationStrategy, times(1)).sendNotification("Test message");
    }
}
