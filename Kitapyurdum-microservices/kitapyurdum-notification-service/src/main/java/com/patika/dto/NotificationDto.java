package com.patika.dto;

import com.patika.dto.enums.NotificationType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NotificationDto {

    private NotificationType notificationType;
    private List<ProductDto> productDtoList;
    private BigDecimal totalAmount;
    private String message;  // Mesaj alanı eklendi
}
