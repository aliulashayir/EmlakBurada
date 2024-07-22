package com.patika.strategy;

import jakarta.mail.internet.MimeMessage;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationStrategyTest {

    @InjectMocks
    private EmailNotificationStrategy emailNotificationStrategy;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotification() throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo("recipient@example.com");
        helper.setSubject("Notification");
        helper.setText("Test Message", true);

        doNothing().when(mailSender).send(mimeMessage);

        emailNotificationStrategy.sendNotification("Test Message");

        verify(mailSender, times(1)).send(mimeMessage);
    }
}
