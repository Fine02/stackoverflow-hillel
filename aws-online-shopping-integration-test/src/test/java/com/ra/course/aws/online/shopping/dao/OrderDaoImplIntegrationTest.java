package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")

public class OrderDaoImplIntegrationTest {
    @Primary
    @Bean
    public NotificationDao mockedNotificationDao() {
        return mock(NotificationDao.class);
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
    private OrderDao orderDao;

    LocalDateTime orderlog_id1 = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
    LocalDateTime orderlog_id2 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime orderlog_id3 = LocalDateTime.of(2020, 3, 21, 22, 22, 11);

    private OrderLog makeOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(orderNumber, creationDate, status);
    }

    public List<OrderLog> makeOrderLogList() {
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(ORDER_LOG1);
        orderLogList.add(ORDER_LOG2);
        orderLogList.add(ORDER_LOG3);
        return orderLogList;
    }

    public OrderLog ORDER_LOG1 = makeOrderLog("2", orderlog_id1, OrderStatus.PENDING);
    public OrderLog ORDER_LOG2 = makeOrderLog("2", orderlog_id2, OrderStatus.PENDING);
    public OrderLog ORDER_LOG3 = makeOrderLog("2", orderlog_id3, OrderStatus.PENDING);


    @Test
    public void getInstanceIsFoundMemberIDTest() {
        boolean result = orderDao.isFoundMemberID(3L);
        System.out.println(result);
    }

    @Test
    public void getInstancefindLogListByOrderTest() {
        List<OrderLog> result = orderDao.findLogListByOrder(makeOrderLogList());
        System.out.println(result);
    }

    ///пересмотреть, т.к. все подрят проходит тест
    @Test
    public void getInstanceTest() {
        Order result = orderDao.findByOrderNumber("4");
        System.out.println(result);
    }

    @Test
    public void getInstanceByOrderLogById() {
        OrderLog result = orderDao.findOrderLogById(3L);
        System.out.println(result);
    }

}
