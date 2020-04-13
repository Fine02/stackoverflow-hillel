package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.*;
import com.ra.course.aws.online.shopping.service.*;
import com.ra.course.aws.online.shopping.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest (classes = AwsOnlineShoppingApplication.class)
public class SpringBootContextTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void checkContextForNotificationService() {
        assertNotNull(context.getBean(NotificationService.class));
    }

    @Test
    void checkContextForOrderService() {
        assertNotNull(context.getBean(OrderService.class));
    }

    @Test
    void checkContextForPaymentService() {
        assertNotNull(context.getBean(PaymentService.class));
    }

    @Test
    void checkContextForShippingService() {
        assertNotNull(context.getBean(ShippingService.class));
    }

    @Test
    public void ifNotificationServiceImplSendMessageThenReturnItsBean() {
        assertEquals(NotificationServiceImpl.class, context.getBean(NotificationServiceImpl.class).getClass());
    }

    @Test
    void checkContextForAccountService() {
        assertNotNull(context.getBean(AccountService.class));
    }

    @Test
    void checkContextForCatalogService() {
        assertNotNull(context.getBean(CatalogService.class));
    }

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
}
