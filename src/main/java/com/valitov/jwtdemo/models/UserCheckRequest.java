package com.valitov.jwtdemo.models;

public class UserCheckRequest {

    private String token;

    private String email;

    public UserCheckRequest() {
    }

    public UserCheckRequest(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String uuid) {
        this.email = uuid;
    }
}
