package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.mapper.EmailNotificationRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetStringFromObjectRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcNotificationDaoImpl implements NotificationDao {
    private static final String INSERT_INTO_NOTIFICATION_TABLE_BY_SMS_NOTIFICATION = "INSERT INTO notification (createdOn, content) VALUES (?, ?) RETURNING notification.id";
    private static final String INSERT_INTO_SMS_NOTIFICATION_TABLE = "INSERT INTO sms_notification (phone, notification_id) VALUES (?, ?)";
    private static final String GET_DATA_FROM_TABLES_OF_NOTIFICATION_FOR_SMS = "SELECT n. createdOn, n. content, sn.phone FROM sms_notification sn JOIN notification n ON sn.notification_id = n.id WHERE n.id=?";


    private static final String INSERT_INTO_NOTIFICATION_TABLE_BY_EMAIL_NOTIFICATION = "INSERT INTO notification (createdOn, content) VALUES (?, ?) RETURNING notification.id";
    private static final String INSERT_INTO_EMAIL_NOTIFICATION_TABLE = "INSERT INTO email_notification (email, notification_id) VALUES (?, ?)";
    private static final String GET_DATA_FROM_TABLES_OF_NOTIFICATION_FOR_EMAIL = "SELECT n. createdOn, n. content, en.email FROM email_notification en JOIN notification n ON en.notification_id = n.id WHERE n.id=?";

    private static final String GET_EMAIL_OF_MEMBER = "SELECT a.email FROM member m JOIN account a ON m.account_id= a.id WHERE a.email=?";
    private static final String GET_PHONE_NUMBER_OF_MEMBER = "SELECT a.phone FROM member m JOIN account a ON m.account_id= a.id WHERE a.phone=?";

    private final GetStringFromObjectRowMapper getStringFromObjectRowMapper;
    private final GetLastIdRowMapper getLastIdRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcNotificationDaoImpl(GetLastIdRowMapper getLastIdRowMapper, EmailNotificationRowMapper emailNotificationRowMapper, GetStringFromObjectRowMapper getStringFromObjectRowMapper, JdbcTemplate jdbcTemplate) {
        this.getLastIdRowMapper = getLastIdRowMapper;
        this.getStringFromObjectRowMapper = getStringFromObjectRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SMSNotification createSMSNotification(SMSNotification smsNotification) {
        try {
            Integer lastInsertId = jdbcTemplate.queryForObject(INSERT_INTO_NOTIFICATION_TABLE_BY_SMS_NOTIFICATION, getLastIdRowMapper, smsNotification.getCreatedOn(), smsNotification.getContent());
            jdbcTemplate.update(INSERT_INTO_SMS_NOTIFICATION_TABLE, smsNotification.getPhone(), lastInsertId);
            SMSNotification result = jdbcTemplate.queryForObject(GET_DATA_FROM_TABLES_OF_NOTIFICATION_FOR_SMS, BeanPropertyRowMapper.newInstance(SMSNotification.class), lastInsertId);
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public EmailNotification createEmailNotification(EmailNotification emailNotification) {
        try {
            Integer lastInsertId = jdbcTemplate.queryForObject(INSERT_INTO_NOTIFICATION_TABLE_BY_EMAIL_NOTIFICATION, getLastIdRowMapper, emailNotification.getCreatedOn(), emailNotification.getContent());
            jdbcTemplate.update(INSERT_INTO_EMAIL_NOTIFICATION_TABLE, emailNotification.getEmail(), lastInsertId);
            EmailNotification result = jdbcTemplate.queryForObject(GET_DATA_FROM_TABLES_OF_NOTIFICATION_FOR_EMAIL, BeanPropertyRowMapper.newInstance(EmailNotification.class), lastInsertId);
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public String foundMemberEmail(String string) {
        try {
            return jdbcTemplate.queryForObject(GET_EMAIL_OF_MEMBER, getStringFromObjectRowMapper, string);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public String foundMemberPhoneNumber(String string) {
        try {
            return jdbcTemplate.queryForObject(GET_PHONE_NUMBER_OF_MEMBER, getStringFromObjectRowMapper, string);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
