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
    public Order mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final OrderStatus orderStatus =mapToOrderStatusForLog (rs);
        final LocalDateTime time = getLocalDate(rs);
        final Order order = new Order();
        order.setOrderNumber(rs.getString("orderNumber"));
        order.setStatus(orderStatus);
        order.setOrderDate(time);
        return order;
    }

    private OrderStatus mapToOrderStatusForLog(final ResultSet rs) throws SQLException {
        final var status = rs.getString("status");
        return status ==null? null: OrderStatus.valueOf(OrderStatus.class, status);
    }

    private LocalDateTime getLocalDate(final ResultSet rs) throws SQLException {
        final Timestamp ts = rs.getTimestamp("orderDate");
        return ts == null ? null : ts.toLocalDateTime();
    }
}
