package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class JdbcPaymentDaoImpl implements PaymentDao {
    public static final String INSERT_PAYMENT = "INSERT INTO payment (payment_status_id, amount) VALUES (?, ?)";
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
            "LEFT JOIN account_status acs ON a.account_status_id = acs.id\n" +
            "LEFT JOIN address adr ON a.address_id = adr.id\n" +
            "LEFT JOIN credit_card crc ON crc.account_id = a.id\n" +
            "LEFT JOIN electronic_bank_transfer ebt ON ebt.account_id = a.id\n" +
            "WHERE m.id=? ";

    private transient final JdbcTemplate jdbcTemplate;
    private transient final GetLastIdRowMapper getLastId;
    private transient final MemberBooleanRowMapper memberBoolean;
    private transient final KeyHolderFactory keyHolderFactory;


    @Autowired
    public JdbcPaymentDaoImpl(final JdbcTemplate jdbcTemplate, final GetLastIdRowMapper getLastId, final MemberBooleanRowMapper memberBoolean, final KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.getLastId = getLastId;
        this.memberBoolean = memberBoolean;
        this.keyHolderFactory = keyHolderFactory;
    }

    @Override
    public void createTransaction(final ElectronicBankTransaction bankTransaction) {
        final Integer paymentStatusID = jdbcTemplate.queryForObject(GET_STATUS_ID, getLastId, bankTransaction.getStatus().toString());
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement pstm = con.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS);
                pstm.setInt(1, paymentStatusID);
                pstm.setDouble(2, bankTransaction.getAmount());
                return pstm;
            }
        }, keyHolder);
        final Long insertId = keyHolder.getKey().longValue();
        jdbcTemplate.update(INSERT_ETRANS, insertId);
    }

    @Override
    public void createTransaction(final CreditCardTransaction cardTransaction) {
        final Integer paymentStatusID = jdbcTemplate.queryForObject(GET_STATUS_ID, getLastId, cardTransaction.getStatus().toString());
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement pstm = con.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS);
                pstm.setInt(1, paymentStatusID);
                pstm.setDouble(2, cardTransaction.getAmount());
                return pstm;
            }
        }, keyHolder);

        final Long insertId = keyHolder.getKey().longValue();
        jdbcTemplate.update(INSERT_CTRANS, insertId);
    }

    @Override
    public boolean isFoundMemberID(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_MEMBER_DATA, memberBoolean, id);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
