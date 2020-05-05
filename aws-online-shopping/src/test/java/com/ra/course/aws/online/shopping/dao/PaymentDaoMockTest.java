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
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

//    @Test
//    public void testCreateElectronicBankTransaction() {
//        //given
//        Integer paymentStatusID = 5;
//        Integer lastInsertPaymentId = 8;
//        Double amount = 8549.77;
//        ElectronicBankTransaction bankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
//        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_STATUS_ID, getLastIdRowMapper, bankTransaction.getStatus().toString())).thenReturn(paymentStatusID);
//        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.INSERT_PAYMENT, getLastIdRowMapper, paymentStatusID, bankTransaction.getAmount())).thenReturn(lastInsertPaymentId);
//        //when
//        paymentDao.createTransaction(bankTransaction);
//        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_ETRANS, lastInsertPaymentId);
//    }


    @Test
    public void testCreateElectronicBankTransaction() throws SQLException {
        //given
        Integer paymentStatusID = 5;
        Integer lastInsertPaymentId = 8;
        Double amount = 8549.77;
        ElectronicBankTransaction bankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);

        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        // KeyHolder mockKey = mock(KeyHolder.class);
        Number mockNumber = mock(Number.class);
        //неизвестно тот ли класс коннекшен
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        PreparedStatementCreator mockPrCreator = mock(PreparedStatementCreator.class);

//        Map<String,Object> map = new HashMap<>();
//        map.put("id",lastInsertPaymentId);
//        when(keyHolder.getKeys()).thenReturn(lastInsertPaymentId);
        //  when(mockKey.getKey()).thenReturn(lastInsertPaymentId);

        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_STATUS_ID, getLastIdRowMapper, bankTransaction.getStatus().toString())).thenReturn(paymentStatusID);

        //  when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(lastInsertPaymentId);


        //JdbcPaymentDaoImpl.mockPreparedStatement.RETURN_GENERATED_KEYS

//        when(mockConnection.prepareStatement(
//                eq(JdbcPaymentDaoImpl.INSERT_PAYMENT))).thenReturn(mockPreparedStatement);


        // KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        //  when(keyHolder.getKeys()).thenReturn(keyHolder);

        //   when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);

        //  when(keyHolder.getKeys()).thenReturn(lastInsertPaymentId);

        Map<String, Object> map = new HashMap<>();
        map.put("id", 8L);
        when(keyHolder.getKeys()).thenReturn(map);
        when(keyHolder.getKey()).thenReturn(8L);

        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(8);

        //  assertEquals(1L, userDAO.createUser(user));


        //   when(keyHolder.getKey()).thenReturn(lastInsertPaymentId);

//        doAnswer((Answer)invocation -> {
//                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
//                    verify(mockPreparedStatement).setInt(1, paymentStatusID);
//                    verify(mockPreparedStatement).setDouble(2, bankTransaction.getAmount());
//                    verify(mockPreparedStatement).executeUpdate();
//                    return null;
//               }).when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)));

        //  }). when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class)));

        // эта штука вообще не нужна
        // when(bankTransaction.getId()).thenReturn((long) lastInsertPaymentId);

        // when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.INSERT_PAYMENT, getLastIdRowMapper, paymentStatusID, bankTransaction.getAmount())).thenReturn(lastInsertPaymentId);
        //when
        paymentDao.createTransaction(bankTransaction);
        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_ETRANS, lastInsertPaymentId);
    }

//    @Test
//    public void testCreateCreditCardTransaction() {
//        //given
//        Integer paymentStatusID = 5;
//        Integer lastInsertPaymentId = 5;
//        Double amount = 9554.77;
//        CreditCardTransaction cardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);
//        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_STATUS_ID, getLastIdRowMapper, cardTransaction.getStatus().toString())).thenReturn(paymentStatusID);
//        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.INSERT_PAYMENT, getLastIdRowMapper, paymentStatusID, cardTransaction.getAmount())).thenReturn(lastInsertPaymentId);
//        //when
//        paymentDao.createTransaction(cardTransaction);
//        verify(jdbcTemplate).update(JdbcPaymentDaoImpl.INSERT_CTRANS, lastInsertPaymentId);
//    }


    @Test
    public void testCreateCreditCardTransaction() throws SQLException {
        //given
        Integer paymentStatusID = 5;
        Integer lastInsertPaymentId = 5;
        Double amount = 9554.77;

        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        CreditCardTransaction cardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);
        when(jdbcTemplate.queryForObject(JdbcPaymentDaoImpl.GET_STATUS_ID, getLastIdRowMapper, cardTransaction.getStatus().toString())).thenReturn(paymentStatusID);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(
                eq(JdbcPaymentDaoImpl.INSERT_PAYMENT), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(lastInsertPaymentId.longValue());

        //when
        paymentDao.createTransaction(cardTransaction);

        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
                    verify(mockPreparedStatement).setInt(1, paymentStatusID);
                    verify(mockPreparedStatement).setDouble(2, cardTransaction.getAmount());
                    verify(mockPreparedStatement, times(1)).setInt(any(Integer.class), any(Integer.class));
                    verify(mockPreparedStatement, times(1)).setDouble(any(Integer.class), any(Double.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

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
