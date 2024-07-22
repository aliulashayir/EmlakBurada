package com.patika.converter;

import com.patika.dto.request.ProductSaveRequest;
import com.patika.dto.response.ProductResponse;
import com.patika.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductSaveRequest request) {
        return Product.builder()
                .name(request.getName())
                .amount(request.getAmount())
                .description(request.getDescription())
                .publisherId(request.getPublisherId()) // Publisher ID ekleniyor
                .build();
    }

    public static ProductResponse toResponse(Product product, String publisherName) {
        return ProductResponse.builder()
                .name(product.getName())
                .amount(product.getAmount())
                .description(product.getDescription())
                .publisherName(publisherName) // Publisher ismi ekleniyor
                .build();
    }
}
