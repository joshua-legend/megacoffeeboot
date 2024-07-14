package com.example.megacoffee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse1<T> {
    private String status;
    private int code;
    private int total;
    private T data;
}