package com.patika.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaveRequest {
    private String name;
    private BigDecimal amount;
    private String description;
    private Long publisherId; // Publisher ID ekleniyor
}
