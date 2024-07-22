package com.patika.controller;

import com.patika.converter.PublisherConverter;
import com.patika.dto.request.PublisherSaveRequest;
import com.patika.dto.response.ProductResponse;
import com.patika.dto.response.PublisherResponse;
import com.patika.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PublisherSaveRequest request) {
        publisherService.save(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponse>> getAll() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> getById(@PathVariable int id) {
        PublisherResponse publisherResponse = publisherService.getById(id)
                .map(PublisherConverter::toResponse)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        return ResponseEntity.ok(publisherResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PublisherResponse> getByName(@PathVariable String name) {
        PublisherResponse publisherResponse = publisherService.getByName(name)
                .map(PublisherConverter::toResponse)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        return ResponseEntity.ok(publisherResponse);
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<List<ProductResponse>> getProductsByPublisherName(@PathVariable String name) {
        return ResponseEntity.ok(publisherService.getProductsByPublisherName(name));
    }

    @GetMapping("/products/id/{id}")
    public ResponseEntity<List<ProductResponse>> getProductsByPublisherId(@PathVariable int id) {
        return ResponseEntity.ok(publisherService.getProductsByPublisherId(id));
    }
}
