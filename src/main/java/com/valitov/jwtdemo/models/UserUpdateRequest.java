package com.valitov.jwtdemo.models;

public class UserUpdateRequest {

    private String token;

    private String email;

    private String uuid;



    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String token, String uuid, String email) {
        this.token = token;
        this.uuid = uuid;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
