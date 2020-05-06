package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcPaymentDaoImpl;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.MemberBooleanRowMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import static org.mockito.Mockito.*;

public class PaymentDaoMockTest {
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    PaymentDao paymentDao;
    private KeyHolderFactory keyHolderFactory = mock(KeyHolderFactory.class);

    private GetLastIdRowMapper getLastIdRowMapper = mock(GetLastIdRowMapper.class);
    private MemberBooleanRowMapper memberBooleanRowMapper = mock(MemberBooleanRowMapper.class);

    @BeforeEach
    public void before() {
        paymentDao = new JdbcPaymentDaoImpl(jdbcTemplate, getLastIdRowMapper, memberBooleanRowMapper, keyHolderFactory);
    }

    @Test
    public void testCreateElectronicBankTransaction() throws SQLException{
        //given
        Integer paymentStatusID = 2;
        Integer lastInsertPaymentId = 8;
        Double amount = 8549.77;
        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        ElectronicBankTransaction bankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_STATUS_ID, getLastIdRowMapper, bankTransaction.getStatus().toString())).thenReturn(paymentStatusID);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(
                eq(JdbcPaymentDaoImpl.INSERT_PAYMENT), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
                    verify(mockPreparedStatement).setInt(1, paymentStatusID);
                    verify(mockPreparedStatement).setDouble(2, bankTransaction.getAmount());
                    verify(mockPreparedStatement, times(1)).setInt(any(Integer.class), any(Integer.class));
                    verify(mockPreparedStatement, times(1)).setDouble(any(Integer.class), any(Double.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(lastInsertPaymentId.longValue());

        //when
        paymentDao.createTransaction(bankTransaction);
        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_ETRANS, keyHolder.getKey());
    }



    @Test
    public void testCreateCreditCardTransaction() throws SQLException {
        //given
        Integer paymentStatusID = 2;
        Integer lastInsertPaymentId = 5;
        Double amount = 9554.77;

        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        CreditCardTransaction cardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_STATUS_ID, getLastIdRowMapper, cardTransaction.getStatus().toString())).thenReturn(paymentStatusID);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(
                eq(JdbcPaymentDaoImpl.INSERT_PAYMENT), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);

        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
                    verify(mockPreparedStatement).setInt(1, paymentStatusID);
                    verify(mockPreparedStatement).setDouble(2, cardTransaction.getAmount());
                    verify(mockPreparedStatement, times(1)).setInt(any(Integer.class), any(Integer.class));
                    verify(mockPreparedStatement, times(1)).setDouble(any(Integer.class), any(Double.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(lastInsertPaymentId.longValue());

        //when
        paymentDao.createTransaction(cardTransaction);
        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_CTRANS, keyHolder.getKey());
    }

    @Test
    public void testIsFoundMemberIDReturnTrue() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_MEMBER_DATA, memberBooleanRowMapper, memberId)).thenReturn(true);
        //when
        var result = paymentDao.isFoundMemberID(memberId);
        Assert.assertTrue(result == true);
    }

    @Test
    public void testIsFoundMemberIDReturnFalse() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_MEMBER_DATA, memberBooleanRowMapper, memberId)).thenReturn(false);
        //when
        var result = paymentDao.isFoundMemberID(memberId);
        Assert.assertEquals(false, result);
    }
}
