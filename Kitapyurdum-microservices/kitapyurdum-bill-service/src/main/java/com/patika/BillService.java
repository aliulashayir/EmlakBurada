package com.patika;

import com.patika.config.RabbitMQConfig;
import com.patika.dto.request.BillRequest;
import com.patika.dto.response.OrderResponse;
import com.patika.dto.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillService {

    private final RestTemplate restTemplate;

    @RabbitListener(queues = RabbitMQConfig.BILL_QUEUE)
    public void generateBill(BillRequest billRequest) {
        Long orderId = billRequest.getOrderId();

        // Order detaylarını alalım
        String orderServiceUrl = "http://kitapyurdum-order-service/api/v1/orders/" + orderId;
        OrderResponse order = restTemplate.getForObject(orderServiceUrl, OrderResponse.class);

        // Fatura oluşturma işlemleri
        String billDetails = "Fatura Detayları: \n" +
                "Order ID: " + order.getId() + "\n" +
                "Customer ID: " + order.getCustomerId() + "\n" +
                "Total Amount: " + order.getTotalAmount() + "\n" +
                "Products: " + order.getProductList().toString();

        // Faturayı Notification Service'e gönderelim
        sendNotification(order.getCustomerEmail(), "Fatura Bilgileri", billDetails);
    }

    private void sendNotification(String to, String subject, String message) {
        // Notification Service URL
        String notificationServiceUrl = "http://kitapyurdum-notification-service/api/v1/notifications/send";

        // Notification Request
        NotificationRequest notificationRequest = new NotificationRequest(to, subject, message);

        // Post Request
        restTemplate.postForObject(notificationServiceUrl, notificationRequest, Void.class);
    }
}
