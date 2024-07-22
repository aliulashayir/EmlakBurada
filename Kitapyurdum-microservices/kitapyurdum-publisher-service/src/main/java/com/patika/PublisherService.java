package com.patika;

import com.patika.converter.PublisherConverter;
import com.patika.dto.request.PublisherSaveRequest;
import com.patika.dto.response.ProductResponse;
import com.patika.dto.response.PublisherResponse;
import com.patika.model.Publisher;
import com.patika.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final RestTemplate restTemplate;

    public void save(PublisherSaveRequest request) {
        Publisher publisher = PublisherConverter.toPublisher(request);
        publisherRepository.save(publisher);
        log.info("Publisher saved: {}", publisher);
    }

    public Optional<Publisher> getByName(String publisherName) {
        return publisherRepository.findByName(publisherName);
    }

    public Optional<Publisher> getById(int id) {
        return publisherRepository.findById(id);
    }

    public List<PublisherResponse> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream()
                .map(PublisherConverter::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByPublisherName(String publisherName) {
        Optional<Publisher> publisherOptional = publisherRepository.findByName(publisherName);
        if (publisherOptional.isPresent()) {
            String publisherServiceUrl = "http://kitapyurdum-product-service/api/v1/products/publisher/name/" + publisherOptional.get().getId();
            ProductResponse[] products = restTemplate.getForObject(publisherServiceUrl, ProductResponse[].class);
            return List.of(products);
        } else {
            log.error("Publisher not found with name: {}", publisherName);
            throw new RuntimeException("Publisher not found");
        }
    }

    public List<ProductResponse> getProductsByPublisherId(int id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            String publisherServiceUrl = "http://kitapyurdum-product-service/api/v1/products/publisher/id/" + id;
            ProductResponse[] products = restTemplate.getForObject(publisherServiceUrl, ProductResponse[].class);
            return List.of(products);
        } else {
            log.error("Publisher not found with id: {}", id);
            throw new RuntimeException("Publisher not found");
        }
    }
}
