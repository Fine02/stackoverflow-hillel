package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcNotificationDaoImpl;
import com.ra.course.aws.online.shopping.dao.impl.JdbcPaymentDaoImpl;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.EmailNotificationRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetStringFromObjectRowMapper;
import com.ra.course.aws.online.shopping.mapper.SMSNotificationRowMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class NotificationDaoMockTest {
    NotificationDao notificationDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private GetStringFromObjectRowMapper getStringMapper = mock(GetStringFromObjectRowMapper.class);
    private EmailNotificationRowMapper emailMapper = mock(EmailNotificationRowMapper.class);
    private SMSNotificationRowMapper smsMapper = mock(SMSNotificationRowMapper.class);
    private KeyHolderFactory keyHolderFactory = mock(KeyHolderFactory.class);

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);
    String content = "some content + test+3";
    String phoneNumber = "38012345111";
    String email = "111j@gmail.com";

    @BeforeEach
    public void before() {
        notificationDao = new JdbcNotificationDaoImpl(getStringMapper, smsMapper, emailMapper, jdbcTemplate, keyHolderFactory);
    }

    @Test
    public void testFoundMemberPhoneNumber() {
        //given
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_MEMBER_PHONE, getStringMapper, phoneNumber)).thenReturn(phoneNumber);
        //when
        String result = notificationDao.foundMemberPhoneNumber(phoneNumber);
        Assert.assertEquals(phoneNumber, result);
    }

    @Test
    public void testFoundMemberEmail() {
        //given
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_MEMBER_EMAIL, getStringMapper, email)).thenReturn(email);
        //when
        String result = notificationDao.foundMemberEmail(email);
        Assert.assertEquals(email, result);
    }

    @Test
    public void testCreateEmailNotification() throws SQLException {
        //given
        Integer lastInsertId = 4;
        EmailNotification emailNotification = new EmailNotification(time, content, email);
        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(
                eq(JdbcNotificationDaoImpl.INSERT_NOTIFIC), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
                    verify(mockPreparedStatement).setTimestamp(1, Timestamp.valueOf(emailNotification.getCreatedOn()));
                    verify(mockPreparedStatement).setString(2, emailNotification.getContent());
                    verify(mockPreparedStatement, times(1)).setTimestamp(any(Integer.class), any(Timestamp.class));
                    verify(mockPreparedStatement, times(1)).setString(any(Integer.class), any(String.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(lastInsertId.longValue());
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_EMAIL, emailMapper, keyHolder.getKey())).thenReturn(emailNotification);

        //when
        EmailNotification result = notificationDao.createEmailNotification(emailNotification);
        verify(jdbcTemplate).update(JdbcNotificationDaoImpl.INSERT_ENOTIFIC, emailNotification.getEmail(), keyHolder.getKey());
        Assert.assertEquals(emailNotification , result);
    }

    @Test
    public void testCreateSMSNotification() throws SQLException{
        //given
        Integer lastInsertId = 5;
        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        SMSNotification smsNotification = new SMSNotification(time, content, phoneNumber);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(
                eq(JdbcNotificationDaoImpl.INSERT_NOTIFIC), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
                    verify(mockPreparedStatement).setTimestamp(1, Timestamp.valueOf(smsNotification.getCreatedOn()));
                    verify(mockPreparedStatement).setString(2, smsNotification.getContent());
                    verify(mockPreparedStatement, times(1)).setTimestamp(any(Integer.class), any(Timestamp.class));
                    verify(mockPreparedStatement, times(1)).setString(any(Integer.class), any(String.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(lastInsertId.longValue());

        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_SMS, smsMapper, keyHolder.getKey())).thenReturn(smsNotification);
        //when
        SMSNotification result = notificationDao.createSMSNotification(smsNotification);
        verify(jdbcTemplate).update(JdbcNotificationDaoImpl.INSERT_SNOTIFIC, smsNotification.getPhone(), keyHolder.getKey());
        Assert.assertEquals(smsNotification, result);
    }
}
