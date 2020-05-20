package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmailNotificationRowMapperMockTest {
    EmailNotificationRowMapper emailNotificationRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        emailNotificationRowMapper = mock(EmailNotificationRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        EmailNotification eNotification = new EmailNotification(time,"some content","jj@gmail.com");
        when(rs.getString("email")).thenReturn("jj@gmail.com");
        when((rs.getTimestamp("createdOn"))).thenReturn(Timestamp.valueOf(time));
        when(rs.getString("content")).thenReturn("some content");

        EmailNotification emailNotification = new EmailNotificationRowMapper().mapRow(rs,0);
        Assertions.assertEquals(eNotification, emailNotification);
    }

    @Test
    public void testWithNullValueMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        EmailNotification eNotification = new EmailNotification();
        when(rs.getString("email")).thenReturn(null);
        when((rs.getTimestamp("createdOn"))).thenReturn(null);
        when(rs.getString("content")).thenReturn(null);

        EmailNotification emailNotification = new EmailNotificationRowMapper().mapRow(rs,0);
        Assertions.assertEquals(eNotification, emailNotification);
    }

}
