package com.balaji.interceptors.request;

import java.security.Principal;

public class User implements Principal {

    private String name;
    private int accountId;

    public User(String name, int accountId) {
        this.name = name;
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
