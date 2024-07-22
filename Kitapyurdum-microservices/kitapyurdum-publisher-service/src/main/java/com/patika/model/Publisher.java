package com.patika.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Publisher {
    private int id;
    private String name;
    private LocalDate creatDate;
}
