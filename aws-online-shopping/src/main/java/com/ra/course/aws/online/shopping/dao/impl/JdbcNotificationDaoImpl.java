package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcNotificationDaoImpl implements NotificationDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcNotificationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SMSNotification createSMSNotification(SMSNotification smsNotification) {
        return null;
    }

    @Override
    public EmailNotification createEmailNotification(EmailNotification emailNotification) {
        return null;
    }

    @Override
    public String foundMemberEmail(String string) {
        return null;
    }

    @Override
    public String foundMemberPhoneNumber(String string) {
        return null;
    }
}
