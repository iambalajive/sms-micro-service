package com.balaji.dao;

import com.balaji.dao.mapper.AccountResultMapper;
import com.balaji.dao.model.Account;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.List;

public class AccountDAO {

    private DBI dbi;

    public AccountDAO(DBI dbi) {
        this.dbi = dbi;
    }

    public List<Account> getAccountByUserName(String userName) {
        Handle handle = this.dbi.open();
        try {
            return handle.createQuery("select * from ACCOUNT where username = ?")
                    .bind(0, userName)
                    .map(new AccountResultMapper()).list();
        } finally {
            handle.close();
        }
    }
}
