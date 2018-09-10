package com.balaji.dao.model;

public class PhoneNumber {

    private int id;
    private String number;
    private int accountId;

    public PhoneNumber(int id, String number, int accountId) {
        this.id = id;
        this.number = number;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public int getAccountId() {
        return accountId;
    }
}
