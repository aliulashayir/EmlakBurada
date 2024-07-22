package com.patika.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Magazine extends Product {
    private String category;

    @Builder(builderMethodName = "magazineBuilder")
    public Magazine(String name, BigDecimal amount, String description, Long publisherId, String category) {
        super(name, amount, description, publisherId);
        this.category = category;
    }
}
