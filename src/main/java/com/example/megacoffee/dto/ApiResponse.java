package com.example.megacoffee.dto;

import com.example.megacoffee.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private Status status;
    private String message;
    private T data;
}