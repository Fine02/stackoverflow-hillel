package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OrderLogRowMapper implements RowMapper<OrderLog> {

    @Override
    public OrderLog mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final OrderStatus orderStatus = mapToOrderStatusForLog(rs);
        final OrderLog orderLog = new OrderLog();
        final LocalDateTime time = getLocalDate(rs);
        orderLog.setId(rs.getInt("id"));
        orderLog.setOrderNumber(rs.getString("orderNumber"));
        orderLog.setCreationDate(time);
        orderLog.setStatus(orderStatus);
        return orderLog;
    }
    private OrderStatus mapToOrderStatusForLog(final ResultSet rs) throws SQLException {
        final var status = rs.getString("status");
        return status ==null? null: OrderStatus.valueOf(OrderStatus.class, status);
    }

    private LocalDateTime getLocalDate(final ResultSet rs) throws SQLException {
        final Timestamp ts = rs.getTimestamp("creationDate");
        return ts == null ? null : ts.toLocalDateTime();
    }
}
