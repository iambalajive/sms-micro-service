package com.balaji.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Message {

    @NotEmpty
    @Length(max = 16,min = 6,message = "field is invalid")
    private String from;

    @NotEmpty
    @Length(max = 16,min = 6,message = "field is invalid")
    private String to;

    @NotEmpty
    @Length(max = 120,min = 1,message = "field is invalid")
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
