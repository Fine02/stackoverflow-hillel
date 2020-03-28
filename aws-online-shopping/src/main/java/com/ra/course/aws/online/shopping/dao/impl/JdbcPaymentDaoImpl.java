package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcPaymentDaoImpl implements PaymentDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPaymentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member foundMemberById(Long memberId) {
        return null;
    }

    @Override
    public List<ElectronicBankTransfer> foundListOfBankTransfer(List<ElectronicBankTransfer> bankTransfers) {
        return null;
    }

    @Override
    public List<CreditCard> foundListOfCreditCard(List<CreditCard> creditCards) {
        return null;
    }

    @Override
    public void update(ElectronicBankTransaction bankTransaction) {

    }

    @Override
    public void update(CreditCardTransaction cardTransaction) {

    }
}
