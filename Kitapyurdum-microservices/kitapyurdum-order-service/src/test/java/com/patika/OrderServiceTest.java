package com.patika;

import com.patika.config.RabbitMQConfig;
import com.patika.dto.request.OrderSaveRequest;
import com.patika.dto.response.OrderResponse;
import com.patika.model.Order;
import com.patika.repository.OrderRepository;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(InstancioExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        OrderSaveRequest request = Instancio.create(OrderSaveRequest.class);
        Order order = Instancio.create(Order.class);

        doAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(order.getId()); // Set the ID after saving
            return null;
        }).when(orderRepository).save(any(Order.class));

        doNothing().when(rabbitTemplate).convertAndSend(anyString(), Optional.ofNullable(any()));

        orderService.save(request);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(rabbitTemplate, times(1)).convertAndSend(Optional.ofNullable(eq(RabbitMQConfig.BILL_QUEUE)), any());
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = Instancio.ofList(Order.class).size(3).create();
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderResponse> result = orderService.getAllOrders();
        assertEquals(orders.size(), result.size());
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Order order = Instancio.create(Order.class);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        OrderResponse result = orderService.getById(id);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    public void testGetOrdersByCustomerId() {
        Long customerId = 1L;
        List<Order> orders = Instancio.ofList(Order.class).size(3).create();
        when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

        List<OrderResponse> result = orderService.getOrdersByCustomerId(customerId);
        assertEquals(orders.size(), result.size());
    }
}
