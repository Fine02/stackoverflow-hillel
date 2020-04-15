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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")

public class OrderDaoImplIntegrationTest {

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

    LocalDateTime time1 = LocalDateTime.of(2020, 3, 19, 22, 25, 25);
    LocalDateTime time2 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 3, 21, 22, 22, 11);
    LocalDateTime time4 = LocalDateTime.of(2020, 2, 15, 10, 12, 10);

    private OrderLog makeOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(orderNumber, creationDate, status);
    }

    private OrderLog makeOrderLog(long id, String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(id, orderNumber, creationDate, status);
    }

    public List<OrderLog> makeOrderLogList() {
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(ORDER_LOG1);
        orderLogList.add(ORDER_LOG2);
        orderLogList.add(ORDER_LOG3);
        return orderLogList;
    }

    public OrderLog ORDER_LOG1 = makeOrderLog("2", time1, OrderStatus.PENDING);
    public OrderLog ORDER_LOG2 = makeOrderLog("2", time2, OrderStatus.PENDING);
    public OrderLog ORDER_LOG3 = makeOrderLog("2", time3, OrderStatus.PENDING);
    public OrderLog ORDER_LOG4 = makeOrderLog(1L, "2", time3, OrderStatus.PENDING);
    public OrderLog ORDER_LOG5 = makeOrderLog("3", time4, OrderStatus.COMPLETE);

    Order order = new Order("3", OrderStatus.SHIPPED, time1);

    //work correct
    @Test
    public void updateOrderTest() {
        orderDao.updateOrder(order);
    }

    //work correct
    @Test
    public void getInstanceIsFoundMemberIDTest() {
        boolean result = orderDao.isFoundMemberID(3L);
        System.out.println(result);
    }

    //work correct
    @Test
    public void getInstanceOfFindingLogListByOrderTest() {
        List<OrderLog> result = orderDao.findLogListByOrder(makeOrderLogList());
        System.out.println(result);
    }

    //work correct
    @Test
    public void getInstanceTest() {
        Order result = orderDao.findByOrderNumber("2");
        System.out.println(result);
    }

    //work correct
    @Test
    public void getInstanceByOrderLogById() {
        OrderLog result = orderDao.findOrderLogById(3L);
        System.out.println(result);
    }

    //work correct
    @Test
    public void isThisOrderLogExistTest() {
        boolean result = orderDao.isThisOrderLogExist(ORDER_LOG4);
        System.out.println(result);
    }

    //work correct
    @Test
    public void addOrderLogAndUpdateOrderTest() {
        orderDao.addOrderLogAndUpdateOrder(order, ORDER_LOG5);
    }
}
