package com.example.megacoffee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage<T> {
    private Status status;
    private String message;
    private T data;
}