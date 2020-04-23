package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcPaymentDaoImpl;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapperMockTest;
import com.ra.course.aws.online.shopping.mapper.MemberBooleanRowMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;

public class PaymentDaoMockTest {
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    PaymentDao paymentDao;
    private GetLastIdRowMapper getLastIdRowMapper;
    private MemberBooleanRowMapper memberBooleanRowMapper;

    @BeforeEach
    public void before() {
        paymentDao = new JdbcPaymentDaoImpl(jdbcTemplate, getLastIdRowMapper, memberBooleanRowMapper);
        getLastIdRowMapper = mock(GetLastIdRowMapper.class);
        memberBooleanRowMapper = mock(MemberBooleanRowMapper.class);
    }

    @Test
    public void testCreateElectronicBankTransaction() {
        //given
        Integer paymentStatusID = null;
        Integer lastInsertPaymentId = null;
        Double amount = 8549.77;
        ElectronicBankTransaction bankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_ID_OF_PAYMENT_STATUS, getLastIdRowMapper, bankTransaction.getStatus().toString())).thenReturn(paymentStatusID);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.INSERT_DATA_INTO_PAYMENT_TABLE, getLastIdRowMapper, paymentStatusID, bankTransaction.getAmount())).thenReturn(lastInsertPaymentId);
        //when
        paymentDao.createTransaction(bankTransaction);
        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_DATA_INTO_ELECTRONIC_BANK_TRANSACTION, lastInsertPaymentId);
    }

    @Test
    public void testCreateCreditCardTransaction() {
        //given
        Integer paymentStatusID = null;
        Integer lastInsertPaymentId = null;
        Double amount = 9554.77;
        CreditCardTransaction cardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_ID_OF_PAYMENT_STATUS, getLastIdRowMapper, cardTransaction.getStatus().toString())).thenReturn(paymentStatusID);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.INSERT_DATA_INTO_PAYMENT_TABLE, getLastIdRowMapper, paymentStatusID, cardTransaction.getAmount())).thenReturn(lastInsertPaymentId);
        //when
        paymentDao.createTransaction(cardTransaction);
        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_DATA_INTO_CREDIT_CARD_TRANSACTION, lastInsertPaymentId);
    }

    @Test
    public void testIsFoundMemberID() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_DATA_OF_MEMBER_BY_ID, memberBooleanRowMapper, memberId)).thenReturn(false);
        //when
        var result = paymentDao.isFoundMemberID(memberId);
        Assert.assertTrue(result == false);
    }
}
