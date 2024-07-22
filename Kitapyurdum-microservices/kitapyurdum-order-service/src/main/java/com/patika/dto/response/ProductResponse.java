package com.patika.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String description;
    private String publisherName;
    private String category;
    private String authorName;
}
