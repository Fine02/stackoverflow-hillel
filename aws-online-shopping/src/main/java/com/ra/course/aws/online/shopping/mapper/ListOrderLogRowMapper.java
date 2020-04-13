package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListOrderLogRowMapper implements ResultSetExtractor<List<OrderLog>> {

    private OrderStatus mapToOrderStatusForLog(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(OrderStatus.class, rs.getString(4));
        return orderStatus;
    }

    @Override
    public List<OrderLog> extractData(ResultSet rs) throws SQLException, DataAccessException {
        OrderStatus orderStatus = mapToOrderStatusForLog(rs);
        List<OrderLog> orderLogList = new ArrayList<>();
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderNumber(rs.getString(2));
        orderLog.setCreationDate(rs.getTimestamp(3).toLocalDateTime());
        orderLog.setStatus(orderStatus);
        orderLogList.add(orderLog);
        return orderLogList;
    }
}

