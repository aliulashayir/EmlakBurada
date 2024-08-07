package com.patika.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherResponse {
    private String name;
    private LocalDate creatDate;
}
