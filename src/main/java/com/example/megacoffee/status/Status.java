package com.example.megacoffee.status;

public enum Status {
    SUCCESS(200, "Success"),
    ERROR(500, "Error"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized");

    private final int code;
    private final String description;

    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
