package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponse<T> {
    private String status;
    private T data;
    private ErrorResponse error;
}
