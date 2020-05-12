package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public ProductDao mockedProductDao() {
        return mock(ProductDao.class);
    }

    @Bean
    public ShoppingCartDao mockedShoppingCartDao() {
        return mock(ShoppingCartDao.class);
    }
}