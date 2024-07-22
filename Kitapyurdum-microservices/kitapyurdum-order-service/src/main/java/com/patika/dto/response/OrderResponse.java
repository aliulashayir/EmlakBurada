package com.patika.dto.response;

import com.patika.constants.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private LocalDateTime createDate;
    private List<String> productNames; // Bu alanı ekledik
    private String orderCode;
    private OrderStatus orderStatus;

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
