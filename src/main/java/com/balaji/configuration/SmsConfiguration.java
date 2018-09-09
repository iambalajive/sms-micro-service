package com.balaji.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class SmsConfiguration extends Configuration {


    @JsonProperty
    private DbConfig dbConfig;


    public DbConfig getDbConfig() {
        return dbConfig;
    }

}
