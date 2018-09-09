package com.balaji.resources.models;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SmsResponse {

    private String message = StringUtils.EMPTY;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        if (error == null) {
            this.error = new ArrayList<>();
        }
        this.error = error;
    }

    private List<String> error = new ArrayList<>();

}
