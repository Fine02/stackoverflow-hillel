package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class EmailNotificationRowMapper implements RowMapper<EmailNotification> {
    @Override
    public EmailNotification mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final LocalDateTime time = getLocalDate(rs);
        final EmailNotification emailNotification = new EmailNotification();
        emailNotification.setEmail(rs.getString("email"));
        emailNotification.setCreatedOn(time);
        emailNotification.setContent(rs.getString("content"));
        return emailNotification;
    }

    private LocalDateTime getLocalDate(final ResultSet rs) throws SQLException {
        final Timestamp ts = rs.getTimestamp("createdOn");
        return ts == null ? null : ts.toLocalDateTime();
    }
}
