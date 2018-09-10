package com.balaji.dao;

import com.balaji.dao.mapper.PhoneNumberResultMapper;
import com.balaji.dao.model.PhoneNumber;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.List;

public class PhoneNumberDAO {

    private DBI dbi;

    public PhoneNumberDAO(DBI dbi) {
        this.dbi = dbi;
    }

    public List<PhoneNumber> findByAccountIdAndNumber(int accountId, String phoneNumber) {
        Handle handle = this.dbi.open();
        try {
            return handle.createQuery("select * from phone_number where account_id = :accountId and number = :number")
                    .bind("accountId", accountId)
                    .bind("number", phoneNumber)
                    .map(new PhoneNumberResultMapper())
                    .list();
        } finally {
            handle.close();
        }
    }
}
