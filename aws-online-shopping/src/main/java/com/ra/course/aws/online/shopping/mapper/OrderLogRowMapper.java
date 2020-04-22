package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class OrderLogRowMapper implements RowMapper<OrderLog> {

    @Override
    public OrderLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = mapToOrderStatusForLog(rs, rowNum);
        OrderLog orderLog = new OrderLog();
        orderLog.setId(rs.getInt("id"));
        orderLog.setOrderNumber(rs.getString("orderNumber"));
        orderLog.setCreationDate(rs.getTimestamp("creationDate").toLocalDateTime());
        orderLog.setStatus(orderStatus);
        return orderLog;
    }
    private OrderStatus mapToOrderStatusForLog(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(OrderStatus.class, rs.getString("status"));
        return orderStatus;
    }
}
