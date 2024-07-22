package com.patika.converter;

import com.patika.dto.request.PublisherSaveRequest;
import com.patika.dto.response.PublisherResponse;
import com.patika.model.Publisher;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublisherConverter {

    public static Publisher toPublisher(PublisherSaveRequest request) {
        return Publisher.builder()
                .name(request.getName())
                .creatDate(request.getCreateDate())
                .build();
    }

    public static PublisherResponse toResponse(Publisher publisher) {
        return PublisherResponse.builder()
                .name(publisher.getName())
                .creatDate(publisher.getCreatDate())
                .build();
    }
}
