package com.balaji.interceptors.request;

import com.balaji.dao.AccountDAO;
import com.balaji.dao.model.Account;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.List;
import java.util.Optional;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {

    private AccountDAO accountDAO;

    public BasicAuthenticator(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }


    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) {
        List<Account> accounts = accountDAO.getAccountByUserName(basicCredentials.getUsername());

        if (accounts.isEmpty()) {
            return Optional.empty();
        }

        Account account = accounts.get(0);

        if (basicCredentials.getPassword().equals(account.getAuthId())) {
            return Optional.of(new User(basicCredentials.getUsername(), account.getId()));
        }
        return Optional.empty();
    }
}
