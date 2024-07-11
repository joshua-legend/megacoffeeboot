package com.example.megacoffee.dto;

import com.example.megacoffee.status.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private ResponseStatus responseStatus;
    private String message;
    private T data;
}