package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public NotificationDao mockedNotificationDao() {
        return mock(NotificationDao.class);
    }

    @Bean
    public OrderDao mockedOrderDao() {
        return mock(OrderDao.class);
    }

    @Bean
    public PaymentDao mockedPaymentDao() {
        return mock(PaymentDao.class);
    }

    @Bean
    public ShippingDao mockedShippingDao() {
        return mock(ShippingDao.class);
    }

    @Bean
    public AccountDao mockedAccountDao() {
        return mock(AccountDao.class);
    }

//    @Bean
//    public ProductDao mockedProductDao() {
//        return mock(ProductDao.class);
//    }

    @Bean
    public ShoppingCartDao mockedShoppingCartDao() {
        return mock(ShoppingCartDao.class);
    }
}

