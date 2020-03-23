package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.service.ProductService;
import com.ra.course.aws.online.shopping.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class SpringBootContextTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void checkContextForProductService() {
        assertNotNull(context.getBean(ProductService.class));
    }
    @Test
    void checkContextForShoppingCartService() {
        assertNotNull(context.getBean(ShoppingCartService.class));
    }


    @TestConfiguration
    static class SpringBootContextTestConfiguration {

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
}
