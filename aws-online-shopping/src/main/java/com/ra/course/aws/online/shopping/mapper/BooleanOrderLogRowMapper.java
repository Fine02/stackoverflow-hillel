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
public class BooleanOrderLogRowMapper implements RowMapper<Boolean> {

    @Override
    public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderLog orderLog = mapRowOrderLog(rs, rowNum);
        if (orderLog != null & orderLog.getId()!=0) {
            return true;
        }
        return false;
    }

    private OrderStatus mapToOrderStatusForLog(ResultSet rs, int rowNum) throws SQLException {
        var status = rs.getString("status");
        return status ==null? null: OrderStatus.valueOf(OrderStatus.class, status);
    }

    private LocalDateTime getLocalDate(ResultSet rs, int i) throws SQLException {
        Timestamp ts = rs.getTimestamp("creationDate");
        return ts == null ? null : ts.toLocalDateTime();
    }

    public OrderLog mapRowOrderLog(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = mapToOrderStatusForLog(rs, rowNum);
        LocalDateTime time = getLocalDate(rs, rowNum);
        OrderLog orderLog = new OrderLog();
        orderLog.setId(rs.getInt("id"));
        orderLog.setOrderNumber(rs.getString("orderNumber"));
        orderLog.setCreationDate(time);
        orderLog.setStatus(orderStatus);
        return orderLog;
    }
}
