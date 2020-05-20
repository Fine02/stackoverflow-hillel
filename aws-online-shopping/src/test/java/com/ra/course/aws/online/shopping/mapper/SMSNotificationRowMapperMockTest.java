package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SMSNotificationRowMapperMockTest {
    SMSNotificationRowMapper smsNotificationRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);
    @BeforeEach
    public void before() {
        smsNotificationRowMapper = mock(SMSNotificationRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        SMSNotification smsNotification = new SMSNotification( time,"some content","38555");
        when(rs.getString("phone")).thenReturn("38555");
        when((rs.getTimestamp("createdOn"))).thenReturn(Timestamp.valueOf(time));
        when(rs.getString("content")).thenReturn("some content");

        SMSNotification sms = new SMSNotificationRowMapper().mapRow(rs,0);
        System.out.println(sms);
        Assertions.assertEquals(smsNotification, sms);
    }

    @Test
    public void testWithNullValueMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        SMSNotification notification = new SMSNotification();
        when(rs.getString("phone")).thenReturn(null);
        when((rs.getTimestamp("createdOn"))).thenReturn(null);
        when(rs.getString("content")).thenReturn(null);

        SMSNotification sms = new SMSNotificationRowMapper().mapRow(rs,0);
        System.out.println(sms);
        Assertions.assertEquals(notification, sms);
    }

}
