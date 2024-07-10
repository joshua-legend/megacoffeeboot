package com.example.megacoffee;

public enum Status {
    OK("OK"),
    ERROR("ERROR");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}