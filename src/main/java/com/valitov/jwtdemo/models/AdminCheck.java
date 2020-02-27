package com.valitov.jwtdemo.models;

public class AdminCheck {
    private String token;

    public AdminCheck() {
    }

    public AdminCheck(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
