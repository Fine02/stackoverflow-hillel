package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        OrderStatus orderStatus = mapToOrderStatus1(rs, rowNum);
        OrderStatus orderStatus2 = mapToOrderStatusForLog(rs);
        List<OrderLog> logList = new ArrayList<>();
        order.setOrderNumber(rs.getString("orderNumber"));
        order.setStatus(orderStatus);
        order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
        OrderLog ol = new OrderLog();
        ol.setId(rs.getInt("orderLog_id"));
        ol.setOrderNumber(rs.getString("orderNumber"));
        ol.setCreationDate(rs.getTimestamp("creationDate").toLocalDateTime());
        ol.setStatus(orderStatus2);
        logList.add(ol);

        order.setOrderLog(logList);
        return order;
    }

    private OrderStatus mapToOrderStatus1(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        return orderStatus;
    }

    private OrderStatus mapToOrderStatusForLog(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(OrderStatus.class, rs.getString("orderLogStatus"));
        return orderStatus;
    }

}


