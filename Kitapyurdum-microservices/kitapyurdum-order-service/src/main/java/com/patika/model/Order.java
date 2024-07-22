package com.patika.model;

import com.patika.constants.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime createDate;
    private List<Long> productIds; // Bu alanı değiştirdik
    private String orderCode;
    private OrderStatus orderStatus;

    public BigDecimal getTotalAmount() {
        // Product servisiyle entegrasyon gerekebilir.
        return BigDecimal.ZERO;
    }
}
