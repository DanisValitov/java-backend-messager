package com.valitov.jwtdemo.models;

import com.valitov.jwtdemo.entities.MyMessage;

public class DeleteMessageRequest {

    private MyMessage message;

    private  String token;

    public DeleteMessageRequest() {
    }

    public DeleteMessageRequest(MyMessage message, String token) {
        this.message = message;
        this.token = token;
    }

    public MyMessage getMessage() {
        return message;
    }

    public void setMessage(MyMessage message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
