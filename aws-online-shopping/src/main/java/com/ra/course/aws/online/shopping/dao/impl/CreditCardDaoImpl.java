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

    @Value("${insertCreditCard}")
    private transient String insertCreditCard;
    @Value("${deleteCreditCard}")
    private transient String deleteCreditCard;

    private transient final JdbcTemplate jdbcTemplate;

    public CreditCardDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(final Long accountId, final CreditCard card, final Long billingAddressId) {
        jdbcTemplate.update(insertCreditCard, card.getNameOnCard(), card.getCardNumber(), card.getCode(),
                billingAddressId.intValue(), accountId.intValue());
        return true;
    }

    @Override
    public boolean remove(final String cardNumber) {
        jdbcTemplate.update(deleteCreditCard, cardNumber);
        return true;
    }
}
