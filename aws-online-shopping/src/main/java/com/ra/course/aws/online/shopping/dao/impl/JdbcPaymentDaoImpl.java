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
    public static final String INSERT_DATA_INTO_PAYMENT_TABLE = "INSERT INTO payment (payment_status_id, amount) VALUES (?, ?) RETURNING payment.id";
    public static final String GET_ID_OF_PAYMENT_STATUS = "SELECT ps.id FROM payment_status ps WHERE ps.status=?";
    public static final String INSERT_DATA_INTO_ELECTRONIC_BANK_TRANSACTION = "INSERT INTO electronic_bank_transaction (payment_id) VALUES (?)";
    public static final String INSERT_DATA_INTO_CREDIT_CARD_TRANSACTION = "INSERT INTO credit_card_transaction (payment_id) VALUES (?)";
    public static final String GET_DATA_OF_MEMBER_BY_ID = " SELECT \n" +
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

    private final JdbcTemplate jdbcTemplate;
    private final GetLastIdRowMapper getLastIdRowMapper;
    private final MemberBooleanRowMapper memberBooleanRowMapper;

    @Autowired
    public JdbcPaymentDaoImpl(JdbcTemplate jdbcTemplate, GetLastIdRowMapper getLastIdRowMapper, MemberBooleanRowMapper memberBooleanRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.getLastIdRowMapper = getLastIdRowMapper;
        this.memberBooleanRowMapper = memberBooleanRowMapper;
    }

    @Override
    public void createTransaction(ElectronicBankTransaction bankTransaction) {
            Integer paymentStatusID = jdbcTemplate.queryForObject(GET_ID_OF_PAYMENT_STATUS, getLastIdRowMapper, bankTransaction.getStatus().toString());
            Integer lastInsertPaymentId = jdbcTemplate.queryForObject(INSERT_DATA_INTO_PAYMENT_TABLE, getLastIdRowMapper, paymentStatusID, bankTransaction.getAmount());
            jdbcTemplate.update(INSERT_DATA_INTO_ELECTRONIC_BANK_TRANSACTION, lastInsertPaymentId);
    }

    @Override
    public void createTransaction(CreditCardTransaction cardTransaction) {
            Integer paymentStatusID = jdbcTemplate.queryForObject(GET_ID_OF_PAYMENT_STATUS, getLastIdRowMapper, cardTransaction.getStatus().toString());
            Integer lastInsertPaymentId = jdbcTemplate.queryForObject(INSERT_DATA_INTO_PAYMENT_TABLE, getLastIdRowMapper, paymentStatusID, cardTransaction.getAmount());
            jdbcTemplate.update(INSERT_DATA_INTO_CREDIT_CARD_TRANSACTION, lastInsertPaymentId);
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        try {
            var result = jdbcTemplate.queryForObject(GET_DATA_OF_MEMBER_BY_ID, memberBooleanRowMapper, id);
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }catch (NullPointerException ex) {
            return false;
        }
    }
}
