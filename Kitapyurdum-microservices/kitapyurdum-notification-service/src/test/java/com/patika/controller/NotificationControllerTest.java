package com.patika.controller;

import com.patika.dto.enums.NotificationType;
import com.patika.model.Notification;
import com.patika.service.NotificationService;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@ExtendWith(InstancioExtension.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Test
    void sendNotification() throws Exception {
        NotificationType type = NotificationType.EMAIL;
        String message = "Test message";

        mockMvc.perform(post("/api/v1/notifications/send")
                        .param("type", type.name())
                        .param("message", message))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).sendNotification(type, message);
    }

    @Test
    void getFailedNotifications() throws Exception {
        List<Notification> failedNotifications = Instancio.ofList(Notification.class).size(3).create();
        when(notificationService.getFailedNotifications()).thenReturn(failedNotifications);

        mockMvc.perform(get("/api/v1/notifications/failed"))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).getFailedNotifications();
    }

    @Test
    void retryFailedNotifications() throws Exception {
        doNothing().when(notificationService).retryFailedNotifications();

        mockMvc.perform(post("/api/v1/notifications/retry"))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).retryFailedNotifications();
    }
}
