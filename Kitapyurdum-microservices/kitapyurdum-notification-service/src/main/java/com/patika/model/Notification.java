package com.patika.model;

import com.patika.dto.ProductDto;
import com.patika.dto.enums.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification {
    @Id
    private String id;
    private NotificationType notificationType;
    private List<ProductDto> productDtoList;
    private BigDecimal totalAmount;
    private String message;
    private String status;  // ADDED - status field to track success or failure
}
