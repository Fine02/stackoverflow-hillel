package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcOrderDaoImpl implements OrderDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateOrder(Order orderNumber) {
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        return false;
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
        return null;
    }

    @Override
    public boolean isThisOrderLogExist(OrderLog orderLog) {
        return false;
    }

    @Override
    public void addOrderLog(boolean add) {

    }

    @Override
    public OrderLog findOrderLogById(Long orderLogId) {
        return null;
    }
}
