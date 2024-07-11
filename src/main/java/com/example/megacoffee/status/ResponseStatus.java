package com.example.megacoffee.status;

public enum ResponseStatus {
    SUCCESS(200, "Success"),
    FAIL(400,"Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    ERROR(500, "Error");


    private final int code;
    private final String description;

    ResponseStatus(int code, String description) {
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
