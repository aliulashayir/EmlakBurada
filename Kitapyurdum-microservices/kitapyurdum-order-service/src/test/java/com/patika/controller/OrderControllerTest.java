package com.patika.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.dto.request.OrderSaveRequest;
import com.patika.dto.response.OrderResponse;
import com.patika.OrderService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void save() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(Instancio.create(OrderSaveRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .content(body)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(orderService, times(1)).save(Mockito.any(OrderSaveRequest.class));
    }

    @Test
    void getAllOrders() throws Exception {
        List<OrderResponse> orders = Instancio.ofList(OrderResponse.class).size(3).create();
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById() throws Exception {
        Long id = 1L;
        OrderResponse orderResponse = Instancio.create(OrderResponse.class);
        when(orderService.getById(id)).thenReturn(orderResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", id)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(orderService, times(1)).getById(id);
    }

    @Test
    void getOrdersByCustomerId() throws Exception {
        Long customerId = 1L;
        List<OrderResponse> orders = Instancio.ofList(OrderResponse.class).size(3).create();
        when(orderService.getOrdersByCustomerId(customerId)).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/customer/{customerId}", customerId)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(orderService, times(1)).getOrdersByCustomerId(customerId);
    }
}
