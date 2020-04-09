package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderRowMapper implements RowMapper<Order> {
   public List<OrderLog> logListTest = new ArrayList<>();


    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
    OrderStatus orderStatus = mapToOrderStatus1(rs, rowNum);
    OrderStatus orderStatus2 = mapToOrderStatusForLog(rs);
        List<OrderLog> logList = new ArrayList<>();
       //  OrderLog orderLog = extractData(rs);
       // List<OrderLog> orderLogList =extractData(rs);

     //   while(rs.next()) {
//            OrderLog aa = extractData(rs);
//            logList.add(orderLog);
            order.setOrderNumber(rs.getString("orderNumber"));
          order.setStatus(orderStatus);
            order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
            OrderLog ol = new OrderLog();
            ol.setId(rs.getInt("orderLog_id"));
            ol.setOrderNumber(rs.getString("orderNumber"));
            ol.setCreationDate(rs.getTimestamp("creationDate").toLocalDateTime());
          ol.setStatus(orderStatus2);
            logList.add(ol);
//           order.setOrderLog(logList);
    //    }
        order.setOrderLog(logList);
        return order;
    }

//    @Override
//    public OrderLog extractData(ResultSet rs) throws SQLException, DataAccessException {
//
//        OrderStatus orderStatus = mapToOrderStatusForLog(rs);
//        OrderLog orderLog = new OrderLog();
//        orderLog.setId(rs.getInt("orderLog_id"));
//        orderLog.setOrderNumber(rs.getString(6));
//        orderLog.setCreationDate(rs.getTimestamp(7).toLocalDateTime());
//        orderLog.setStatus(orderStatus);
//        // orderLogList.add(orderLog);
//        logListTest.add(orderLog);
//
//        return orderLog;
//    }


    private OrderStatus mapToOrderStatus1(ResultSet rs, int rowNum) throws SQLException {
         OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        return orderStatus;
    }


    private OrderStatus mapToOrderStatusForLog(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = OrderStatus.valueOf(OrderStatus.class, rs.getString("orderLogStatus"));
        return orderStatus;
    }


//good
//    @Override
//    public OrderLog extractData(ResultSet rs) throws SQLException, DataAccessException {
//        OrderStatus orderStatus = mapToOrderStatusForLog(rs);
//        OrderLog orderLog = new OrderLog();
//                  orderLog.setId(rs.getInt("orderLog_id"));
//                  orderLog.setOrderNumber(rs.getString(6));
//                  orderLog.setCreationDate(rs.getTimestamp(7).toLocalDateTime());
//                  orderLog.setStatus(orderStatus);
//                  // orderLogList.add(orderLog);
//        logListTest.add(orderLog);
//        return orderLog;
//    }


//    private List<OrderLog> mapToOrderLogList(ResultSet rs) throws SQLException, DataAccessException {
//        OrderStatus orderStatus = mapToOrderStatusForLog(rs);
//        List<OrderLog> orderLogList = new ArrayList<>();
//       // OrderLog orderLog = null;
////        while(rs.next()) {
//        OrderLog   orderLog = new OrderLog();
//            orderLog.setId(rs.getInt("orderLog_id"));
//            orderLog.setOrderNumber(rs.getString(6));
//            orderLog.setCreationDate(rs.getTimestamp(7).toLocalDateTime());
//            orderLog.setStatus(orderStatus);
//            orderLogList.add(orderLog);
////        }
//        return orderLogList;
//    }

//   @Override
//    public List<OrderLog> extractData(ResultSet rs) throws SQLException, DataAccessException {
//        List<OrderLog> orderLogList = new ArrayList<>();
//       OrderStatus orderStatus = mapToOrderStatusForLog(rs);
////        while (rs.next()) {
//
//
//            OrderLog orderLog = new OrderLog();
//            orderLog.setId(rs.getInt("orderLog_id"));
//            orderLog.setOrderNumber(rs.getString(6));
//            orderLog.setCreationDate(rs.getTimestamp(7).toLocalDateTime());
//            orderLog.setStatus(orderStatus);
//            orderLogList.add(orderLog);
////        }
//        return orderLogList;
//    }




}


