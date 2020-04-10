package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcNotificationDaoImpl implements NotificationDao {
    private static final String SELECT_ONE_SQL = "";
    private static final String SELECT_ORDER_LOG_SQL = "";
    private static final String SELECT_MEMBER_SQL = "";
    private static final String SELECT_SMS_Notification_SQL = "";
    private static final String GET_PHONE_NUMBER_OF_MEMBER = "SELECT a.phone FROM member m JOIN account a ON m.account_id= a.id WHERE a.phone='38012345222'";


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
