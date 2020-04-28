package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.mapper.EmailNotificationRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetStringFromObjectRowMapper;
import com.ra.course.aws.online.shopping.mapper.SMSNotificationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcNotificationDaoImpl implements NotificationDao {
    public static final String INSERT_NOTIFIC = "INSERT INTO notification (createdOn, content) VALUES (?, ?) RETURNING notification.id";
    public static final String INSERT_SNOTIFIC = "INSERT INTO sms_notification (phone, notification_id) VALUES (?, ?)";
    public static final String GET_SMS = "SELECT n. createdOn, n. content, sn.phone FROM sms_notification sn JOIN notification n ON sn.notification_id = n.id WHERE n.id=?";

    public static final String INSERT_ENOTIFIC = "INSERT INTO email_notification (email, notification_id) VALUES (?, ?)";
    public static final String GET_EMAIL = "SELECT n. createdOn, n. content, en.email FROM email_notification en JOIN notification n ON en.notification_id = n.id WHERE n.id=?";

    public static final String GET_MEMBER_EMAIL = "SELECT a.email FROM member m JOIN account a ON m.account_id= a.id WHERE a.email=?";
    public static final String GET_MEMBER_PHONE = "SELECT a.phone FROM member m JOIN account a ON m.account_id= a.id WHERE a.phone=?";

    private transient final GetStringFromObjectRowMapper getString;
    private transient final GetLastIdRowMapper getLastId;
    private transient final SMSNotificationRowMapper smsMapper;
    private transient final EmailNotificationRowMapper emailMapper;
    private transient final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcNotificationDaoImpl(final GetLastIdRowMapper getLastId, final GetStringFromObjectRowMapper getString, final SMSNotificationRowMapper smsMapper, final EmailNotificationRowMapper emailMapper, final JdbcTemplate jdbcTemplate) {
        this.getLastId = getLastId;
        this.getString = getString;
        this.smsMapper = smsMapper;
        this.emailMapper = emailMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SMSNotification createSMSNotification(final SMSNotification smsNotification) {
        try {
            final Integer lastInsertId = jdbcTemplate.queryForObject(INSERT_NOTIFIC, getLastId, smsNotification.getCreatedOn(), smsNotification.getContent());
            jdbcTemplate.update(INSERT_SNOTIFIC, smsNotification.getPhone(), lastInsertId);
            return jdbcTemplate.queryForObject(GET_SMS, smsMapper, lastInsertId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public EmailNotification createEmailNotification(final EmailNotification emailNotification) {
        try {
            final Integer lastInsertId = jdbcTemplate.queryForObject(INSERT_NOTIFIC, getLastId, emailNotification.getCreatedOn(), emailNotification.getContent());
            jdbcTemplate.update(INSERT_ENOTIFIC, emailNotification.getEmail(), lastInsertId);
            return jdbcTemplate.queryForObject(GET_EMAIL, emailMapper, lastInsertId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public String foundMemberEmail(final String string) {
        try {
            return jdbcTemplate.queryForObject(GET_MEMBER_EMAIL, getString, string);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public String foundMemberPhoneNumber(final String string) {
        try {
            return jdbcTemplate.queryForObject(GET_MEMBER_PHONE, getString, string);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
