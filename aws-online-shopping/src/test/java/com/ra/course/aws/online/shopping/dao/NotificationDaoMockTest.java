package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcNotificationDaoImpl;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.mapper.EmailNotificationRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetStringFromObjectRowMapper;
import com.ra.course.aws.online.shopping.mapper.SMSNotificationRowMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificationDaoMockTest {
    NotificationDao notificationDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private GetStringFromObjectRowMapper getStringFromObjectRowMapper;
    private GetLastIdRowMapper getLastIdRowMapper;
    private EmailNotificationRowMapper emailNotificationRowMapper;
    private SMSNotificationRowMapper smsNotificationRowMapper;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);
    String content = "some content + test+3";
    String phoneNumber = "38012345111";
    String email = "111j@gmail.com";

    @BeforeEach
    public void before() {
        notificationDao = new JdbcNotificationDaoImpl(getLastIdRowMapper, getStringFromObjectRowMapper, emailNotificationRowMapper, smsNotificationRowMapper, jdbcTemplate);
        getStringFromObjectRowMapper = mock(GetStringFromObjectRowMapper.class);
        getLastIdRowMapper = mock(GetLastIdRowMapper.class);
        emailNotificationRowMapper = mock(EmailNotificationRowMapper.class);
        smsNotificationRowMapper = mock(SMSNotificationRowMapper.class);
    }

    @Test
    public void testFoundMemberPhoneNumber() {
        //given
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_PHONE_NUMBER_OF_MEMBER, getStringFromObjectRowMapper, phoneNumber)).thenReturn(phoneNumber);
        //when
        String result = notificationDao.foundMemberPhoneNumber(phoneNumber);
        Assert.assertTrue(result == null);
    }

    @Test
    public void testFoundMemberEmail() {
        //given
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_EMAIL_OF_MEMBER, getStringFromObjectRowMapper, email)).thenReturn(email);
        //when
        String result = notificationDao.foundMemberEmail(email);
        Assert.assertTrue(result == null);
    }

    @Test
    public void testCreateEmailNotification() {
        //given
        var lastInsertId = 5;
        EmailNotification emailNotification = new EmailNotification(time, content, email);
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_DATA_FROM_TABLES_OF_NOTIFICATION_FOR_EMAIL, emailNotificationRowMapper, lastInsertId)).thenReturn(emailNotification);
        //when
        EmailNotification result = notificationDao.createEmailNotification(emailNotification);
        Assert.assertTrue(result == null);
    }

    @Test
    public void testCreateSMSNotification() {
        //given
        var lastInsertId = 5;
        SMSNotification smsNotification = new SMSNotification(time, content, phoneNumber);
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_DATA_FROM_TABLES_OF_NOTIFICATION_FOR_SMS, smsNotificationRowMapper, lastInsertId)).thenReturn(smsNotification);
        //when
        SMSNotification result = notificationDao.createSMSNotification(smsNotification);
        Assert.assertTrue(result == null);
    }
}
