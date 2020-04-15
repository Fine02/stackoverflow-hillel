package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;

import java.util.List;

public interface OrderDao {

    void updateOrder(Order order);

    void addOrderLogAndUpdateOrder(Order order, OrderLog orderLog);

    boolean isFoundMemberID(Long id);

    Order findByOrderNumber(String orderNumber);

    List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList);

    boolean isThisOrderLogExist(OrderLog orderLog);

    OrderLog findOrderLogById(Long orderLogId);

}
