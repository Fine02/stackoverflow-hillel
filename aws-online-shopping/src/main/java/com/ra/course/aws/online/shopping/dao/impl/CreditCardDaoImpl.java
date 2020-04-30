package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.CreditCardDao;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("sql-requests.yml")
public class CreditCardDaoImpl implements CreditCardDao {

    @Value("${INSERT_CREDIT_CARD}")
    private transient String INSERT_CREDIT_CARD;
    @Value("${DELETE_CREDIT_CARD}")
    private transient String DELETE_CREDIT_CARD;

    private transient final JdbcTemplate jdbcTemplate;

    public CreditCardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(Long accountId, CreditCard card, Long billingAddressId) {
        jdbcTemplate.update(INSERT_CREDIT_CARD, card.getNameOnCard(), card.getCardNumber(), card.getCode(),
                billingAddressId.intValue(), accountId.intValue());
        return true;
    }

    @Override
    public boolean remove(String cardNumber) {
        jdbcTemplate.update(DELETE_CREDIT_CARD, cardNumber);
        return true;
    }
}
