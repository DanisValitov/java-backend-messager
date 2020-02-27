package com.valitov.jwtdemo.models;

import java.util.List;

public class AuthenticationResponse {

    private final String jwt;

    private Long expiresIn;

    private String userId;

    private String name;

    private List<String> roles;



    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse(String jwt, Long expiresIn, String userId, String name, List<String> roles) {
        this.jwt = jwt;
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.name = name;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
