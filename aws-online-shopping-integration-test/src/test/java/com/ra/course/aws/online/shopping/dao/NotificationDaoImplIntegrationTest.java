package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class NotificationDaoImplIntegrationTest {

    @Primary
    @Bean
    public PaymentDao mockedPaymentDao() {
        return mock(PaymentDao.class);
    }

    @Primary
    @Bean
    public ShippingDao mockedShippingDao() {
        return mock(ShippingDao.class);
    }

    @Primary
    @Bean
    public AccountDao mockedAccountDao() {
        return mock(AccountDao.class);
    }

    @Primary
    @Bean
    public ProductDao mockedProductDao() {
        return mock(ProductDao.class);
    }

    @Primary
    @Bean
    public ShoppingCartDao mockedShoppingCartDao() {
        return mock(ShoppingCartDao.class);
    }

    @Autowired
    private NotificationDao notificationDao;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);
    String content = "some content + test+3";
    String phoneNumber = "38012345111";
    String email = "111j@gmail.com";


    EmailNotification emailNotification = new EmailNotification(time, content, email);
    SMSNotification smsNotification = new SMSNotification(time, content, phoneNumber);


    //work correct
    @Test
    public void createSMSNotificationTest() {
        SMSNotification result = notificationDao.createSMSNotification(smsNotification);
        System.out.println(result);
    }

    //work correct
    @Test
    public void createEmailNotificationTest() {
        EmailNotification result = notificationDao.createEmailNotification(emailNotification);
        System.out.println(result);
    }

    //work correct
    @Test
    public void foundMemberEmail() {
        String result = notificationDao.foundMemberEmail(email);
        System.out.println(result);
    }

    //work correct
    @Test
    public void foundMemberPhoneNumberTest() {
        String result = notificationDao.foundMemberPhoneNumber(phoneNumber);
        System.out.println(result);
    }

}
