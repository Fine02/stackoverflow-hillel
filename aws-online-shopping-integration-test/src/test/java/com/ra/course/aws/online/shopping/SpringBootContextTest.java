package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.service.NotificationService;
import com.ra.course.aws.online.shopping.service.OrderService;
import com.ra.course.aws.online.shopping.service.PaymentService;
import com.ra.course.aws.online.shopping.service.ShippingService;
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

@SpringBootTest
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

    @TestConfiguration
    static class SpringBootContextTestConfiguration {

        @Primary
        @Bean
        public NotificationDao mockedNotificationDao() {
            return mock(NotificationDao.class);
        }

        @Primary
        @Bean
        public OrderDao mockedOrderDao() {
            return mock(OrderDao.class);
        }

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
    }
}
