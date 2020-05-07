package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.CreditCardDao;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@PropertySource("sql-requests.yml")
public class CreditCardDaoImpl implements CreditCardDao {

    @Value("${insertCreditCard}")
    private transient String insertCreditCard;
    @Value("${deleteCreditCard}")
    private transient String deleteCreditCard;

    private transient final JdbcTemplate jdbcTemplate;
    private transient final KeyHolderFactory keyHolderFactory;

    public CreditCardDaoImpl(final JdbcTemplate jdbcTemplate, KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
    }

    @Override
    public Long save(final Long accountId, final CreditCard card) {
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con)
                    throws SQLException {
                final PreparedStatement ps = con.prepareStatement(insertCreditCard, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, card.getNameOnCard());
                ps.setString(2, card.getCardNumber());
                ps.setInt(3, card.getCode());
                ps.setInt(4, accountId.intValue());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public boolean remove(final String cardNumber) {
        jdbcTemplate.update(deleteCreditCard, cardNumber);
        return true;
    }
}
