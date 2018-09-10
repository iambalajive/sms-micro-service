package com.balaji.dao.model;

public class Account {

    private String username;
    private String authId;
    private int id;

    public Account(String username, String authId, int id) {
        this.username = username;
        this.authId = authId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthId() {
        return authId;
    }
}
