package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPaymentDaoImpl implements PaymentDao {
    public static final String INSERT_PAYMENT = "INSERT INTO payment (payment_status_id, amount) VALUES (?, ?) RETURNING payment.id";
    public static final String GET_STATUS_ID = "SELECT ps.id FROM payment_status ps WHERE ps.status=?";
    public static final String INSERT_ETRANS = "INSERT INTO electronic_bank_transaction (payment_id) VALUES (?)";
    public static final String INSERT_CTRANS = "INSERT INTO credit_card_transaction (payment_id) VALUES (?)";
    public static final String GET_MEMBER_DATA = " SELECT \n" +
            "\t    m.account_id,\n" +
            "        m.id member_id,\n" +
            "        a.userName, a.password,\n" +
            "        acs.status,\n" +
            "        a.name,\n" +
            "        adr.streetAddress, adr.city, adr.state, adr.zipcode, adr.country,\n" +
            "        a.email,\n" +
            "        a.phone,\n" +
            "        crc.nameOnCard, crc.cardNumber,crc.code, \n" +
            "        ebt.bankName, ebt.routingNumber, ebt.accountNumber\n" +
            "FROM member m JOIN account a ON m.account_id= a.id\n" +
            "JOIN account_status acs ON a.account_status_id = acs.id\n" +
            "JOIN address adr ON a.address_id = adr.id\n" +
            "JOIN credit_card crc ON crc.account_id = a.id\n" +
            "JOIN electronic_bank_transfer ebt ON ebt.account_id = a.id\n" +
            "WHERE m.id=? ";

    private transient final JdbcTemplate jdbcTemplate;
    private transient final GetLastIdRowMapper getLastId;
    private transient final MemberBooleanRowMapper memberBoolean;

    @Autowired
    public JdbcPaymentDaoImpl(final JdbcTemplate jdbcTemplate, final GetLastIdRowMapper getLastId, final MemberBooleanRowMapper memberBoolean) {
        this.jdbcTemplate = jdbcTemplate;
        this.getLastId = getLastId;
        this.memberBoolean = memberBoolean;
    }

    @Override
    public void createTransaction(final ElectronicBankTransaction bankTransaction) {
            final Integer paymentStatusID = jdbcTemplate.queryForObject(GET_STATUS_ID, getLastId, bankTransaction.getStatus().toString());
            final Integer lastInsertId = jdbcTemplate.queryForObject(INSERT_PAYMENT, getLastId, paymentStatusID, bankTransaction.getAmount());
            jdbcTemplate.update(INSERT_ETRANS, lastInsertId);
    }

    @Override
    public void createTransaction(final CreditCardTransaction cardTransaction) {
            final Integer paymentStatusID = jdbcTemplate.queryForObject(GET_STATUS_ID, getLastId, cardTransaction.getStatus().toString());
            final Integer lastInsertId = jdbcTemplate.queryForObject(INSERT_PAYMENT, getLastId, paymentStatusID, cardTransaction.getAmount());
            jdbcTemplate.update(INSERT_CTRANS, lastInsertId);
    }

    @Override
    public boolean isFoundMemberID(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_MEMBER_DATA, memberBoolean, id);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }catch (NullPointerException ex) {
            return false;
        }
    }

//    @Override
//    public boolean isFoundMemberID(final Long id) {
//        try {
//            final var result = jdbcTemplate.queryForObject(GET_MEMBER_DATA, memberBoolean, id);
//            return result==null?false:true;
//        } catch (EmptyResultDataAccessException ex) {
//            return false;
//        }
//    }
}
