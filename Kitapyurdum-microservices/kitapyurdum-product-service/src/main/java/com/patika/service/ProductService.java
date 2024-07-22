package com.patika.service;

import com.patika.converter.ProductConverter;
import com.patika.dto.request.ProductSaveRequest;
import com.patika.dto.response.ProductResponse;
import com.patika.dto.response.PublisherResponse;
import com.patika.model.Product;
import com.patika.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductResponse saveProduct(ProductSaveRequest request) {
        Product product = ProductConverter.toProduct(request);
        productRepository.save(product);
        return getProductResponse(product);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            return getProductResponse(product);
        }
        throw new RuntimeException("Product not found");
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.getAll()
                .stream()
                .map(this::getProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(String keyword, String sortBy, String order, Pageable pageable) {
        Set<Product> products = productRepository.getAll().stream()
                .filter(product -> product.getName() != null && product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toSet());

        List<Product> sortedProducts = products.stream()
                .sorted(getComparator(sortBy, order))
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), sortedProducts.size());
        int end = Math.min((start + pageable.getPageSize()), sortedProducts.size());
        Page<Product> page = new PageImpl<>(sortedProducts.subList(start, end), pageable, sortedProducts.size());

        return page.getContent().stream()
                .map(this::getProductResponse)
                .collect(Collectors.toList());
    }

    private Comparator<Product> getComparator(String sortBy, String order) {
        Comparator<Product> comparator;
        switch (sortBy.toLowerCase()) {
            case "amount":
                comparator = Comparator.comparing(Product::getAmount);
                break;
            case "name":
            default:
                comparator = Comparator.comparing(Product::getName);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private ProductResponse getProductResponse(Product product) {
        // Publisher bilgilerini Publisher mikroservisinden alın
        String publisherServiceUrl = "http://kitapyurdum-publisher-service/api/v1/publishers/" + product.getPublisherId();
        PublisherResponse publisher = restTemplate.getForObject(publisherServiceUrl, PublisherResponse.class);
        String publisherName = (publisher != null) ? publisher.getName() : "Unknown";
        return ProductConverter.toResponse(product, publisherName);
    }
}
