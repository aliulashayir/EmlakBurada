package com.patika.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Book extends Product {
    private String category;
    private Author author;

    @Builder(builderMethodName = "bookBuilder")
    public Book(String name, BigDecimal amount, String description, Long publisherId, String category, Author author) {
        super(name, amount, description, publisherId);
        this.category = category;
        this.author = author;
    }
}
