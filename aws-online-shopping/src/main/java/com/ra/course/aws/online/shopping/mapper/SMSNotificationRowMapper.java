package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SMSNotificationRowMapper implements RowMapper<SMSNotification> {
    @Override
    public SMSNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
        SMSNotification smsNotification = new SMSNotification();
        smsNotification.setPhone(rs.getString("phone"));
        smsNotification.setCreatedOn(rs.getTimestamp("createdOn").toLocalDateTime());
        smsNotification.setContent(rs.getString("content"));
        return smsNotification;
    }
}
