//package com.balaji.authentication;
//
//import com.balaji.dao.AccountDAO;
//import com.balaji.dao.model.Account;
//import com.google.common.base.Optional;
//import io.dropwizard.auth.Authenticator;
//import io.dropwizard.auth.basic.BasicCredentials;
//
//public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {
//
//    private AccountDAO accountDAO;
//
//    public BasicAuthenticator(AccountDAO accountDAO) {
//        this.accountDAO = accountDAO;
//    }
//
//    @Override
//    public Optional<User> authenticate(BasicCredentials basicCredentials) {
//        Account account = accountDAO.getAccountByUserName(basicCredentials.getUsername());
//
//        if (basicCredentials.getPassword().equals(account.getAuthId())) {
//            return Optional.of(new User(basicCredentials.getUsername()));
//        }
//        return Optional.absent();
//    }
//
//}
