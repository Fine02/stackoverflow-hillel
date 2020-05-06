package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {
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

}
