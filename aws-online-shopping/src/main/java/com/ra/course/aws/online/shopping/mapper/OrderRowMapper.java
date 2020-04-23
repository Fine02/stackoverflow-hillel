package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus =mapToOrderStatusForLog (rs,rowNum);
        LocalDateTime time = getLocalDate(rs, rowNum);
        Order order = new Order();
        order.setOrderNumber(rs.getString("orderNumber"));
        order.setStatus(orderStatus);
        order.setOrderDate(time);
        return order;
    }

    private OrderStatus mapToOrderStatusForLog(ResultSet rs, int rowNum) throws SQLException {
        var status = rs.getString("status");
        return status ==null? null: OrderStatus.valueOf(OrderStatus.class, status);
    }

    private LocalDateTime getLocalDate(ResultSet rs, int i) throws SQLException {
        Timestamp ts = rs.getTimestamp("orderDate");
        return ts == null ? null : ts.toLocalDateTime();
    }
}
