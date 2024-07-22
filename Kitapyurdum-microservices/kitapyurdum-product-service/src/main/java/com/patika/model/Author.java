package com.patika.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Author {
    private String name;
    private String surname;
    private String bio;
    private Set<Book> books;
}
