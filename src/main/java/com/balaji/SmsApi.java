package com.balaji;

import com.balaji.interceptors.request.BasicAuthenticator;
import com.balaji.interceptors.request.User;
import com.balaji.configuration.SmsConfiguration;
import com.balaji.dao.AccountDAO;
import com.balaji.health.TemplateHealthCheck;
import com.balaji.interceptors.response.ValidationErrorHandler;
import com.balaji.resources.InboundSmsResource;
import com.balaji.resources.OutboundSmsResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
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

        BasicCredentialAuthFilter<User> basicCredentialAuthFilter = new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new BasicAuthenticator(accountDAO))
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter();

        environment.jersey().register(new AuthDynamicFeature(basicCredentialAuthFilter));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(new InboundSmsResource());
        environment.jersey().register(new OutboundSmsResource());
        environment.jersey().register(new ValidationErrorHandler());
    }
}
