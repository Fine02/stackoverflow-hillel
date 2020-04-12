package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

//можно удалить
@Service
public class EmailNotificationRowMapper implements RowMapper<EmailNotification> {
    @Override
    public EmailNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
      // EmailNotification emailNotification = new EmailNotification( rs.getTimestamp("createdOn").toLocalDateTime(), rs.getString("context"), rs.getString("email"));
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setCreatedOn(rs.getTimestamp("createdOn").toLocalDateTime());
        emailNotification.setContent(rs.getString("context"));
        emailNotification.setEmail(rs.getString("email"));
        return emailNotification;
    }
}
