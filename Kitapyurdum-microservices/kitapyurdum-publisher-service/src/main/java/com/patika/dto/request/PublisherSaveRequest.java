package com.patika.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherSaveRequest {
    private String name;
    private LocalDate createDate;
}
