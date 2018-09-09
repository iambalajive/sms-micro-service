package com.balaji.dao.model;

public class Account {

    private String username;
    private String authId;

    public Account(String username, String authId) {
        this.username = username;
        this.authId = authId;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthId() {
        return authId;
    }
}
