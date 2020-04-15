package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.exceptions.OrderIsAlreadyShippedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class OrderServiceImplIntegrationTest {

    @Autowired
    private OrderService orderService;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 25, 25);
    Order order = new Order ("3",OrderStatus.UNSHIPPED, time);
    Order orderIsAlreadyShipped = new Order ("3",OrderStatus.SHIPPED, time);

    @Test
    public void whenSendForShipmentOrderThanOrderStatusChange(){
        orderService.sendForShipment(order);

        assertEquals(order.getStatus(), OrderStatus.SHIPPED);
    }

    @Test
    public void shouldThrowExceptionIfOrderIsAlreadyShipped(){
        Throwable exception = Assertions.assertThrows(OrderIsAlreadyShippedException.class, () -> {
            orderService.sendForShipment(orderIsAlreadyShipped);
        });

        assertEquals(exception.getMessage(), "This Order is already shipped");
        assertEquals(exception.getClass(), OrderIsAlreadyShippedException.class);
    }


}
