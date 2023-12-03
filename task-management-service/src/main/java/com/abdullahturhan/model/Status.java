package com.abdullahturhan.model;

public enum Status {
    GET_PROCESSING("GET PROCESSING"),
    COMPLETED("COMPLETED");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
