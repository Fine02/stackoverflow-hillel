package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;

import java.util.List;

public interface OrderDao {

    void updateOrder(Order orderNumber);

    boolean isFoundMemberID(Long id);

    Order findByOrderNumber(String orderNumber);

    List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList);

    boolean isThisOrderLogExist(OrderLog orderLog);

    void addOrderLog(boolean add);

    OrderLog findOrderLogById(Long orderLogId);

     Order addOrder();
     Order findByOrderNumber(String orderNumber);
     OrderStatus removeOrder(String orderNumber);
     Order updateOrder(String orderNumber);
}
