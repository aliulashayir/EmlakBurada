package com.patika.consumer;

import com.patika.dto.NotificationDto;
import com.patika.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${notification.queue}")
    public void sendNotification(NotificationDto notificationDto) {
        log.info("notification :{}", notificationDto.toString());
        notificationService.sendNotification(notificationDto.getNotificationType(), notificationDto.getMessage());
    }
}
