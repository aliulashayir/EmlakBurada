package com.patika.dto.request;

import com.patika.constants.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSaveRequest {
    private Long customerId;
    private LocalDateTime createDate;
    private List<Long> productIds; // Bu alanı değiştirdik
    private OrderStatus orderStatus;
}
