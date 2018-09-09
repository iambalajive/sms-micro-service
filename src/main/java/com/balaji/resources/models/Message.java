package com.balaji.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String from;
    private String to;
    private String text;

    @JsonProperty
    public String getFrom() {
        return from;
    }
    @JsonProperty
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
