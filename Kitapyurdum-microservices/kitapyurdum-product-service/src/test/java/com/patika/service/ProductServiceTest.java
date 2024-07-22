package com.patika.service;

import com.patika.dto.response.ProductResponse;
import com.patika.dto.response.PublisherResponse;
import com.patika.model.Product;
import com.patika.repository.ProductRepository;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(InstancioExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchProducts() {
        Set<Product> products = new HashSet<>();
        products.add(new Product(1L, "Product 1", BigDecimal.valueOf(10), "Description 1", 1L));
        products.add(new Product(2L, "Product 2", BigDecimal.valueOf(20), "Description 2", 1L));
        products.add(new Product(3L, "Product 3", BigDecimal.valueOf(15), "Description 3", 1L));

        when(productRepository.getAll()).thenReturn(products);
        when(restTemplate.getForObject(anyString(), eq(PublisherResponse.class)))
                .thenReturn(new PublisherResponse("Publisher 1"));

        Pageable pageable = PageRequest.of(0, 10);
        List<ProductResponse> result = productService.searchProducts("Product", "amount", "asc", pageable);

        assertEquals(3, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 3", result.get(1).getName());
        assertEquals("Product 2", result.get(2).getName());

        verify(productRepository, times(1)).getAll();
    }
}
