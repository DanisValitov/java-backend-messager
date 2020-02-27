package com.valitov.jwtdemo.models;

public class BadResponse {

    private final String error;

    public String getError() {
        return error;
    }

    public BadResponse(String error) {
        this.error = error;
    }
}
