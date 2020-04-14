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
public class OrderRowMapper2 implements RowMapper <Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        OrderStatus orderStatus = mapToOrderStatus1(rs, rowNum);
        List<OrderLog> logList = new ArrayList<>();
//        logList.add(mapToOrderLog (rs, rowNum));
        order.setOrderNumber(rs.getString("orderNumber"));
        order.setStatus(orderStatus);
        order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
        if (logList.size()>0) {
            order.setOrderLog(logList);
        }
        return order;
    }

    private OrderStatus mapToOrderStatus1(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        return orderStatus;
    }

    private OrderStatus mapToOrderStatusForLog(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(OrderStatus.class, rs.getString("orderLogStatus"));
        return orderStatus;
    }

//    private OrderLog mapToOrderLog (ResultSet rs, int rowNum) throws SQLException {
//        OrderStatus orderStatus = mapToOrderStatusForLog(rs, rowNum);
//        OrderLog ol = new OrderLog();
//        ol.setId(rs.getInt("orderLog_id"));
//        ol.setOrderNumber(rs.getString("orderNumber"));
//        ol.setCreationDate(rs.getTimestamp("creationDate").toLocalDateTime());
//        ol.setStatus(orderStatus);
//       return ol;
//    }

}
