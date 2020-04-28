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

import static org.mockito.Mockito.*;

public class NotificationDaoMockTest {
    NotificationDao notificationDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private GetStringFromObjectRowMapper getStringMapper = mock(GetStringFromObjectRowMapper.class);
    private GetLastIdRowMapper getLastIdMapper = mock(GetLastIdRowMapper.class);
    private EmailNotificationRowMapper emailMapper = mock(EmailNotificationRowMapper.class);
    private SMSNotificationRowMapper smsMapper = mock(SMSNotificationRowMapper.class);

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);
    String content = "some content + test+3";
    String phoneNumber = "38012345111";
    String email = "111j@gmail.com";

    @BeforeEach
    public void before() {
        notificationDao = new JdbcNotificationDaoImpl(getLastIdMapper, getStringMapper, smsMapper, emailMapper, jdbcTemplate);
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
    public void testCreateEmailNotification() {
        //given
        var lastInsertId = 5;
        EmailNotification emailNotification = new EmailNotification(time, content, email);
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.INSERT_NOTIFIC, getLastIdMapper, emailNotification.getCreatedOn(), emailNotification.getContent())).thenReturn(lastInsertId);
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_EMAIL, emailMapper, lastInsertId)).thenReturn(emailNotification);
        //when

        EmailNotification result = notificationDao.createEmailNotification(emailNotification);
        verify(jdbcTemplate).update(JdbcNotificationDaoImpl.INSERT_ENOTIFIC, emailNotification.getEmail(), lastInsertId);
        Assert.assertEquals(emailNotification , result);
    }

    @Test
    public void testCreateSMSNotification() {
        //given
        var lastInsertId = 5;
        SMSNotification smsNotification = new SMSNotification(time, content, phoneNumber);

        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.INSERT_NOTIFIC, getLastIdMapper, smsNotification.getCreatedOn(), smsNotification.getContent())).thenReturn(lastInsertId);
        when(jdbcTemplate.queryForObject(JdbcNotificationDaoImpl.GET_SMS, smsMapper, lastInsertId)).thenReturn(smsNotification);
        //when
        SMSNotification result = notificationDao.createSMSNotification(smsNotification);
        verify(jdbcTemplate).update(JdbcNotificationDaoImpl.INSERT_SNOTIFIC, smsNotification.getPhone(), lastInsertId);
        Assert.assertEquals(smsNotification, result);
    }
}
