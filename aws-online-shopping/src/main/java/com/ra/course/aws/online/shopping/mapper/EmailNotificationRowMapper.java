package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class EmailNotificationRowMapper implements RowMapper<EmailNotification> {
    @Override
    public EmailNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setEmail(rs.getString("email"));
        emailNotification.setCreatedOn(rs.getTimestamp("createdOn").toLocalDateTime());
        emailNotification.setContent(rs.getString("content"));
        return emailNotification;
    }
}
