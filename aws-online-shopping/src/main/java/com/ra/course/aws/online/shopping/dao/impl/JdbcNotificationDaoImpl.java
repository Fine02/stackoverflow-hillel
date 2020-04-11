package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcNotificationDaoImpl implements NotificationDao {
    //private static final String ADD_ORDER_LOG = "INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ONE_SQL = "";

    private static final String SELECT_MEMBER_SQL = "";
//    private static final String INSERT_INTO_NOTIFICATION_TABLE = "INSERT INTO notification (createOn, content) VALUE (?, ?)";
    private static final String INSERT_INTO_NOTIFICATION_TABLE = "INSERT INTO notification (createdOn, content) VALUES (?, ?) RETURNING notification.id";
   // private static final String GET_LAST_ID="SELECT CURRVAL(pg_get_serial_sequence('notification','id'))";

   // private static final String GET_LAST_ID="INSERT INTO notification (createdOn, content) VALUES ('2020-03-21 22:22:11', 'yykjj kk') RETURNING id";

    private static final String GET_LAST_ID="INSERT INTO notification (createdOn, content) VALUES (?, ?) RETURNING id";

    private static final String GET_EMAIL_OF_MEMBER = "SELECT a.email FROM member m JOIN account a ON m.account_id= a.id WHERE a.email=?";
    private static final String GET_PHONE_NUMBER_OF_MEMBER = "SELECT a.phone FROM member m JOIN account a ON m.account_id= a.id WHERE a.phone=?";

    private final GetLastIdRowMapper getLastIdRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcNotificationDaoImpl(GetLastIdRowMapper getLastIdRowMapper, JdbcTemplate jdbcTemplate) {
        this.getLastIdRowMapper = getLastIdRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SMSNotification createSMSNotification(SMSNotification smsNotification) {
        return null;
    }





    @Override
    public EmailNotification createEmailNotification(EmailNotification emailNotification) {
       // jdbcTemplate.update(INSERT_INTO_NOTIFICATION_TABLE, emailNotification.getNotificationId(), emailNotification.getEmail(), emailNotification.getCreatedOn(), emailNotification.getContent());
    //   Integer lastInsertId = jdbcTemplate.update(INSERT_INTO_NOTIFICATION_TABLE,  emailNotification.getCreatedOn(), emailNotification.getContent());
     //  jdbcTemplate.update(INSERT_INTO_NOTIFICATION_TABLE,  emailNotification.getCreatedOn(), emailNotification.getContent());
      // Integer lastInsertId = jdbcTemplate.queryForObject(SELECT_ORDER_LOG_SQL, getLastIdRowMapper );
   //     Integer number = jdbcTemplate.update(GET_LAST_ID,  emailNotification.getCreatedOn(), emailNotification.getContent());

   //    Integer lastInsertId2 = jdbcTemplate.queryForObject("INSERT INTO notification (createdOn, content) VALUES ('2020-03-21 22:22:11', 'yykjj kk') RETURNING id", getLastIdRowMapper  );

      Integer lastInsertId2 = jdbcTemplate.queryForObject(INSERT_INTO_NOTIFICATION_TABLE, getLastIdRowMapper, emailNotification.getCreatedOn(), emailNotification.getContent());


 //      Integer lastInsertId1 = jdbcTemplate.update(GET_LAST_ID,  emailNotification.getCreatedOn(), emailNotification.getContent(), getLastIdRowMapper);
  //      var queryForRowSet = jdbcTemplate.update(GET_LAST_ID,  emailNotification.getCreatedOn(), emailNotification.getContent(), BeanPropertyRowMapper.newInstance(Integer.class));
    //    Integer lastInsertId1 = jdbcTemplate.queryForObject( BeanPropertyRowMapper.newInstance(Integer.class));
//        Integer lastInsertId = jdbcTemplate.queryForObject("SELECT last");
    //    System.out.println(queryForRowSet);
       System.out.println(lastInsertId2);
        return null;
    }

    @Override
    public String foundMemberEmail(String string) {
        return jdbcTemplate.queryForObject(GET_EMAIL_OF_MEMBER, BeanPropertyRowMapper.newInstance(String.class), string);
    }

    @Override
    public String foundMemberPhoneNumber(String string) {
        return jdbcTemplate.queryForObject(GET_PHONE_NUMBER_OF_MEMBER, BeanPropertyRowMapper.newInstance(String.class), string);
    }


}
