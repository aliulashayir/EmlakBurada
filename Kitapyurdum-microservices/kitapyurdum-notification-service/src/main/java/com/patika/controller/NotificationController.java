package com.patika.controller;

import com.patika.model.Notification;
import com.patika.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/failed")
    public ResponseEntity<List<Notification>> getFailedNotifications() {
        List<Notification> failedNotifications = notificationService.getFailedNotifications();
        return new ResponseEntity<>(failedNotifications, HttpStatus.OK);
    }

    @PostMapping("/retry")
    public ResponseEntity<Void> retryFailedNotifications() {
        notificationService.retryFailedNotifications();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
