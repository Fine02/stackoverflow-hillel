package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.service.*;
import com.ra.course.aws.online.shopping.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes ={AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
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
    void checkContextForProductService() {
        assertNotNull(context.getBean(ProductService.class));
    }

    @Test
    void checkContextForShoppingCartService() {
        assertNotNull(context.getBean(ShoppingCartService.class));
    }

}
