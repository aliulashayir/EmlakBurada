package com.patika.strategy;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationStrategy implements NotificationStrategy {

    private final JavaMailSender mailSender;

    @Override
    public void sendNotification(String message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo("recipient@example.com");  // Bu kısmı dinamik yapabilirsiniz
            helper.setSubject("Notification");
            helper.setText(message, true);

            mailSender.send(mimeMessage);
            log.info("Email sent: {}", message);
        } catch (jakarta.mail.MessagingException e) {
            log.error("Failed to send email: {}", message, e);
        }
    }
}
