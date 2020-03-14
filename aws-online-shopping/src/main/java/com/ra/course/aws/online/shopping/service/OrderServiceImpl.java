package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.OrderNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private transient final OrderDao orderDao;

    public OrderServiceImpl(final OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order cancelOrder(final Order order, final Member member) {
        if ( orderDao.isFoundMemberID(member.getMemberID())){
            final var  foundOrder = orderDao.findByOrderNumber(order.getOrderNumber());
            if (foundOrder.getOrderDate().isBefore(LocalDateTime.now())) {
                foundOrder.setStatus(OrderStatus.CANCELED);
                order.setStatus(OrderStatus.CANCELED);
                orderDao.updateOrder(foundOrder);
                return order;
            }
            throw new OrderNotFoundException( "There is not found the Order by this number" );
        }
        throw new MemberNotFoundException("There is not found the Member by this ID");
    }

    @Override
    public List<OrderLog> getOrderTrack(final String orderNumber) {
        final var foundOrder = orderDao.findByOrderNumber(orderNumber);
        if (foundOrder != null) {
            return orderDao.findLogListByOrder(foundOrder.getOrderLog());
        }
        return Collections.emptyList();
    }
}
