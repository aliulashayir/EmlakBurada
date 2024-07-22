package com.patika;

import com.patika.config.RabbitMQConfig;
import com.patika.constants.OrderStatus;
import com.patika.converter.OrderConverter;
import com.patika.dto.request.BillRequest;
import com.patika.dto.request.OrderSaveRequest;
import com.patika.dto.response.OrderResponse;
import com.patika.dto.response.ProductResponse;
import com.patika.model.Order;
import com.patika.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public void save(OrderSaveRequest request) {
        Order order = OrderConverter.toOrder(request);
        orderRepository.save(order);
        log.info("Order saved: {}", order);

        // Fatura oluşturma işlemi için RabbitMQ'ya mesaj gönderimi
        BillRequest billRequest = new BillRequest(order.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.BILL_QUEUE, billRequest);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::getOrderResponseWithProductNames)
                .collect(Collectors.toList());
    }

    public OrderResponse getById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return getOrderResponseWithProductNames(order.get());
        }
        throw new RuntimeException("Order not found");
    }

    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::getOrderResponseWithProductNames)
                .collect(Collectors.toList());
    }

    public Page<OrderResponse> searchOrders(String orderCode, OrderStatus orderStatus, Pageable pageable) {
        Page<Order> orders;
        if ((orderCode == null || orderCode.isEmpty()) && orderStatus == null) {
            orders = orderRepository.findAll(pageable);
        } else {
            orders = orderRepository.searchOrders(orderCode, orderStatus, pageable);
        }
        return orders.map(this::getOrderResponseWithProductNames);
    }

    private OrderResponse getOrderResponseWithProductNames(Order order) {
        OrderResponse response = OrderConverter.toResponse(order);
        // Product isimlerini alarak OrderResponse'a ekleyelim
        List<String> productNames = order.getProductIds().stream()
                .map(this::getProductName)
                .collect(Collectors.toList());
        response.setProductNames(productNames);
        return response;
    }

    private String getProductName(Long productId) {
        String productServiceUrl = "http://kitapyurdum-product-service/api/v1/products/" + productId;
        ProductResponse productResponse = restTemplate.getForObject(productServiceUrl, ProductResponse.class);
        return productResponse != null ? productResponse.getName() : "Unknown";
    }
}
