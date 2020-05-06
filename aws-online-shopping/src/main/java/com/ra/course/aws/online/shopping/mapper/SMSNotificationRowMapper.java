package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class SMSNotificationRowMapper implements RowMapper<SMSNotification> {
    @Override
    public SMSNotification mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final SMSNotification smsNotification = new SMSNotification();
        final LocalDateTime time = getLocalDate(rs);
        smsNotification.setPhone(rs.getString("phone"));
        smsNotification.setCreatedOn(time);
        smsNotification.setContent(rs.getString("content"));
        return smsNotification;
    }

    private LocalDateTime getLocalDate(final ResultSet rs) throws SQLException {
        final Timestamp ts = rs.getTimestamp("createdOn");
        return ts == null ? null : ts.toLocalDateTime();
    }

}
