package com.balaji.dao.mapper;

import com.balaji.dao.model.Account;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountResultMapper implements ResultSetMapper<Account> {
    @Override
    public Account map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
        return new Account(arg1.getString("username"),
                arg1.getString("auth_id"),arg1.getInt("id"));
    }

}