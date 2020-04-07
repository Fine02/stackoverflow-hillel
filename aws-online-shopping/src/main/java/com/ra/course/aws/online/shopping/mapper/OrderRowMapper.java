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
public class OrderRowMapper implements RowMapper <Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = mapToOrderStatus(rs, rowNum);
        List<OrderLog> orderLogList =mapToOrderLogList(rs, rowNum);
        Order order = new Order();
        order.setOrderNumber(rs.getString("orderNumber"));
        order.setStatus(orderStatus);
        order.setOrderDate(rs.getDate("orderDate"));
        order.getOrderLog(orderLogList);

        return order;
    }

    private OrderStatus mapToOrderStatus(ResultSet rs, int rowNum) throws SQLException {
        //OrderStatus orderStatus =  OrderStatus.valueOf(rs.getString(1));
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("status"));
        return orderStatus;
    }

    private List<OrderLog> mapToOrderLogList(ResultSet rs, int rowNum) throws SQLException {
        OrderStatus orderStatus = mapToOrderStatus(rs, rowNum);
        List<OrderLog> orderLogList = new ArrayList<>();
        OrderLog orderLog = new OrderLog();

        while (rs.next()) {
            orderLog.setId(rs.getInt("orderLog_id"));
            orderLog.setOrderNumber(rs.getString("orderNumber"));
            orderLog.setCreationDate(rs.getDate("creationDate"));
            orderLog.setStatus(orderStatus);
            orderLogList.add(orderLog);
        }
        return orderLogList;

    }

}


