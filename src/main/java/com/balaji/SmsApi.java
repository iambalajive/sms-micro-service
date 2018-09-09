package com.balaji;

import com.balaji.configuration.SmsConfiguration;
import com.balaji.dao.AccountDAO;
import com.balaji.health.TemplateHealthCheck;
import com.balaji.resources.InboundSmsResource;
import com.balaji.resources.OutboundSmsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class SmsApi extends Application<SmsConfiguration> {

    public static void main(String[] args) throws Exception {
        new SmsApi().run(args);
    }

    @Override
    public void initialize(Bootstrap<SmsConfiguration> bootstrap) {

    }

    @Override
    public void run(SmsConfiguration smsConfiguration, Environment environment) throws Exception {

        environment.healthChecks().register("AppHealthCheck", new TemplateHealthCheck());
        AccountDAO accountDAO = new AccountDAO();
//        environment.jersey().register(new BasicAuthProvider<User>(new BasicAuthenticator(accountDAO), "secret"));
        environment.jersey().register(new InboundSmsResource());
        environment.jersey().register(new OutboundSmsResource());
    }
}
