package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
//@ActiveProfiles("local")
@ActiveProfiles("test")
//@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
@Sql({"classpath: mysqlschema.sql", "classpath: mysql-test-data.sql"})

public class OrderDaoImplIntegrationTest {

    @Autowired
    private OrderDao orderDao;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
    LocalDateTime time1 = LocalDateTime.of(2020, 3, 19, 22, 25, 25);
    LocalDateTime time2 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 3, 21, 22, 22, 11);
    LocalDateTime time4 = LocalDateTime.of(2020, 2, 15, 10, 12, 10);

    public OrderLog orderLog1 = new OrderLog(3, "2", time3, OrderStatus.PENDING);
    public OrderLog orderLog2 = new OrderLog(2, "2", time2, OrderStatus.PENDING);
    public OrderLog orderLog3 = new OrderLog(855, "2854", time3, OrderStatus.PENDING);
    public OrderLog orderLog4 = new OrderLog(1L, "1", time, OrderStatus.PENDING);
    public OrderLog orderLog5 = new OrderLog("3", time4, OrderStatus.COMPLETE);

    Order order = new Order("3", OrderStatus.SHIPPED, time1);

    @Test
    public void updateOrderTest() {
        orderDao.updateOrder(order);
    }

    @Test
    public void ifMemberWasFoundByIdThenReturnTrueTest() {
        boolean result = orderDao.isFoundMemberID(3L);

        assertEquals(true, result);
    }

    @Test
    public void ifMemberWasNotFoundByIdThenReturnFalseTest() {
        boolean result = orderDao.isFoundMemberID(8543L);

        assertEquals(false, result);
    }

    @Test
    public void getInstanceOfFindingLogListByOrderTest() {
        List<OrderLog> result = orderDao.findLogListByOrder(makeOrderLogList(orderLog1, orderLog2));

        assertEquals(makeOrderLogList(orderLog1, orderLog2), result);
    }

    @Test
    public void getInstanceOfFoundOrderByOrderNumberTest() {
        Order expectedResult = new Order("1", OrderStatus.UNSHIPPED, time1);
        expectedResult.setOrderLog(makeOrderLogList(orderLog4));

        Order actualResult = orderDao.findByOrderNumber("1");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getNullIfOrderByOrderNumberWasNotFoundTest() {
        Order actualResult = orderDao.findByOrderNumber("95554441");

        assertEquals(null, actualResult);
    }

    @Test
    public void getInstanceOfFoundOrderLogIfItExistTest() {
        OrderLog result = orderDao.findOrderLogById(3L);

        assertEquals(orderLog1, result);
    }

    @Test
    public void getNullIfOrderLogByIdWasNotFound() {
        OrderLog result = orderDao.findOrderLogById(1555L);

        assertEquals(null, result);
    }

    @Test
    public void ifOrderLogExistThenReturnTrueTest() {
        boolean result = orderDao.isThisOrderLogExist(orderLog4);

        assertEquals(true, result);
    }

    @Test
    public void ifOrderLogExistThenReturnFalseTest() {

        boolean result = orderDao.isThisOrderLogExist(orderLog3);

        assertEquals(false, result);
    }

    @Test
    public void addOrderLogAndUpdateOrderTest() {
        orderDao.addOrderLogAndUpdateOrder(order, orderLog5);
    }

    public List<OrderLog> makeOrderLogList(OrderLog orderLog1, OrderLog orderLog2) {
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(orderLog1);
        orderLogList.add(orderLog2);
        return orderLogList;
    }

    public List<OrderLog> makeOrderLogList(OrderLog orderLog) {
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(orderLog);
        return orderLogList;
    }

}
