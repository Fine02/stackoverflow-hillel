package com.ra.course.aws.online.shopping.dao;


import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

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

    //work correct
    @Test
    public void isThisOrderLogExistTest() {

     //   System.out.println(result);
    }

}
