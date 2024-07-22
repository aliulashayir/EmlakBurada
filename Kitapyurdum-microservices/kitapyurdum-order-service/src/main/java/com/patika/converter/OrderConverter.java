package com.patika.converter;

import com.patika.dto.request.OrderSaveRequest;
import com.patika.dto.response.OrderResponse;
import com.patika.model.Order;
import com.patika.util.OrderCodeGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrder(OrderSaveRequest request) {
        return Order.builder()
                .customerId(request.getCustomerId())
                .createDate(request.getCreateDate())
                .productIds(request.getProductIds())
                .orderCode(OrderCodeGenerator.generateOrderCode())
                .orderStatus(request.getOrderStatus())
                .build();
    }

    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .createDate(order.getCreateDate())
                .orderCode(order.getOrderCode())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
