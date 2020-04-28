package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
//@ActiveProfiles("local")
@ActiveProfiles("test")
//@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
@Sql({"classpath: mysqlschema.sql", "classpath: mysql-test-data.sql"})
public class NotificationDaoImplIntegrationTest {

    @Autowired
    private NotificationDao notificationDao;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);
    String content = "some content + test+3";
    String phoneNumber = "38012345111";
    String email = "111j@gmail.com";


    EmailNotification emailNotification = new EmailNotification(time, content, email);
    SMSNotification smsNotification = new SMSNotification(time, content, phoneNumber);


    @Test
    public void createSMSNotificationTest() {
        SMSNotification result = notificationDao.createSMSNotification(smsNotification);

        assertEquals(smsNotification, result);
    }

    @Test
    public void createEmailNotificationTest() {
        EmailNotification result = notificationDao.createEmailNotification(emailNotification);

        assertEquals(emailNotification, result);
    }

    @Test
    public void foundMemberEmailTest() {
        String result = notificationDao.foundMemberEmail(email);

        assertEquals(email, result);
    }

    @Test
    public void ifMemberEmailWasNotFoundThenReturnNullTest() {
        String result = notificationDao.foundMemberEmail("fhhg@gmail.com");

        assertEquals(null, result);
    }

    @Test
    public void foundMemberPhoneNumberTest() {
        String result = notificationDao.foundMemberPhoneNumber(phoneNumber);

        assertEquals(phoneNumber, result);
    }

    @Test
    public void ifMemberPhoneNumberWasNotFoundReturnNullTest() {
        String result = notificationDao.foundMemberPhoneNumber("855654");

        assertEquals(null, result);
    }

}
