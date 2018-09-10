package com.balaji;

import com.balaji.cache.SmsMeterCache;
import com.balaji.cache.StopMessageCache;
import com.balaji.configuration.DbConfig;
import com.balaji.configuration.SmsConfiguration;
import com.balaji.dao.AccountDAO;
import com.balaji.dao.PhoneNumberDAO;
import com.balaji.health.TemplateHealthCheck;
import com.balaji.interceptors.request.BasicAuthenticator;
import com.balaji.interceptors.request.User;
import com.balaji.interceptors.response.ValidationErrorHandler;
import com.balaji.resources.InboundSmsResource;
import com.balaji.resources.OutboundSmsResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;


public class SmsApi extends Application<SmsConfiguration> {

    public static void main(String[] args) throws Exception {
        new SmsApi().run(args);
    }

    @Override
    public void initialize(Bootstrap<SmsConfiguration> bootstrap) {

    }

    @Override
    public void run(SmsConfiguration smsConfiguration, Environment environment) throws Exception {

        DbConfig dbConfig = smsConfiguration.getDbConfig();
        String jdbcUrl = "jdbc:postgresql://" + dbConfig.getHost() + ":" + dbConfig.getPort() + "/" + dbConfig.getDbName();

        DBI dbi = new DBI(jdbcUrl, dbConfig.getUserName(), dbConfig.getPassword());


        //DAO
        AccountDAO accountDAO = new AccountDAO(dbi);
        PhoneNumberDAO phoneNumberDAO = new PhoneNumberDAO(dbi);

        //Cache
        StopMessageCache stopMessageCache = new StopMessageCache(4);
        SmsMeterCache smsMeterCache = new SmsMeterCache(24);

        // Basic Auth
        BasicCredentialAuthFilter<User> basicCredentialAuthFilter = new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new BasicAuthenticator(accountDAO))
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter();

        environment.jersey().register(new AuthDynamicFeature(basicCredentialAuthFilter));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(new InboundSmsResource(stopMessageCache, phoneNumberDAO));
        environment.jersey().register(new OutboundSmsResource(phoneNumberDAO, stopMessageCache, smsMeterCache));
        environment.jersey().register(new ValidationErrorHandler());
        environment.healthChecks().register("AppHealthCheck", new TemplateHealthCheck());

    }
}
