package com.balaji.dao.mapper;

import com.balaji.dao.model.PhoneNumber;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneNumberResultMapper implements ResultSetMapper<PhoneNumber> {
    @Override
    public PhoneNumber map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
        return new PhoneNumber(arg1.getInt("id"),
                arg1.getString("number"), arg1.getInt("account_id"));
    }

}