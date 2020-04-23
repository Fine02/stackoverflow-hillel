package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcOrderDaoImpl;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.mapper.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OrderDaoMockTest {
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    OrderDao orderDao;
    private MemberBooleanRowMapper memberBooleanRowMapper;
    private BooleanOrderLogRowMapper booleanOrderLogRowMapper;
    private GetLastIdRowMapper getIdRowMapper;
    private OrderRowMapper orderRowMapper;
    private OrderLogRowMapper orderLogRowMapper;
    private LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);

    @BeforeEach
    public void before() {
        orderDao = new JdbcOrderDaoImpl(jdbcTemplate, memberBooleanRowMapper, booleanOrderLogRowMapper, getIdRowMapper, orderRowMapper, orderLogRowMapper);
        memberBooleanRowMapper = mock(MemberBooleanRowMapper.class);
        booleanOrderLogRowMapper = mock(BooleanOrderLogRowMapper.class);
        getIdRowMapper = mock(GetLastIdRowMapper.class);
        orderRowMapper = mock(OrderRowMapper.class);
        orderLogRowMapper = mock(OrderLogRowMapper.class);
    }

    @Test
    public void testUpdateOrder() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        Order order = new Order("3", OrderStatus.SHIPPED, time);
        Integer getIdOfOrder = null;
        Integer getNumberFromStatus = null;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_ID, getIdRowMapper, order.getOrderNumber())).thenReturn(getIdOfOrder);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ID_OF_ORDER_STATUS, getIdRowMapper, order.getStatus().toString())).thenReturn(getNumberFromStatus);
        //when
        orderDao.updateOrder(order);
        verify(jdbcTemplate).update(JdbcOrderDaoImpl.UPDATE_ORDER_BY_ORDER_NUMBER, getNumberFromStatus, order.getOrderNumber());
    }

    @Test
    public void testAddOrderLogAndUpdateOrder() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        OrderLog orderLog = new OrderLog("6", time, OrderStatus.COMPLETE);
        Order order = new Order("6", OrderStatus.SHIPPED, time);
        Integer getIdOfOrder = null;
        Integer getNumberOfStatus = null;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_ID, getIdRowMapper, orderLog.getOrderNumber())).thenReturn(getIdOfOrder);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ID_OF_ORDER_STATUS, getIdRowMapper, orderLog.getStatus().toString())).thenReturn(getNumberOfStatus);
        //when
        orderDao.addOrderLogAndUpdateOrder(order, orderLog);
        verify(jdbcTemplate).update(JdbcOrderDaoImpl.UPDATE_ORDER_BY_ORDER_NUMBER, getNumberOfStatus, order.getOrderNumber());
        verify(jdbcTemplate).update(JdbcOrderDaoImpl.INSERT_ORDER_LOG, orderLog.getOrderNumber(), orderLog.getCreationDate(), getNumberOfStatus, getIdOfOrder);
    }

    @Test
    public void testIsFoundMemberID() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_MEMBER_BY_ID, memberBooleanRowMapper, memberId)).thenReturn(true);
        //when
        var result = orderDao.isFoundMemberID(memberId);
        Assert.assertTrue(result == false);
    }

    @Test
    public void testFindByOrderNumber() {
        //given
        var orderNumber = "1";
        List<OrderLog> list = new ArrayList<>();
        list.add(new OrderLog(1, "1", time, OrderStatus.COMPLETE));
        Order order = new Order("1", OrderStatus.SHIPPED, time);
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG_BY_ORDER_NUMBER, orderLogRowMapper, orderNumber)).thenReturn(list);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_BY_ORDER_NUMBER, orderRowMapper, orderNumber)).thenReturn(order);
        //when
        var result = orderDao.findByOrderNumber(orderNumber);
        Assert.assertTrue(result == null);
    }

    @Test
    public void testFindLogListByOrder() {
        //given
        var orderNumber = "1";
        List<OrderLog> list = new ArrayList<>();
        List<OrderLog> logList = new ArrayList<>();
        list.add(new OrderLog(1, "1", time, OrderStatus.COMPLETE));
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG_BY_ORDER_NUMBER, orderLogRowMapper, orderNumber)).thenReturn(list);
        //when
        var result = orderDao.findLogListByOrder(list);
        assertThat(result).isNotNull().isEqualTo(logList);
    }

    @Test
    public void testIsThisOrderLogExist() {
        //given
        OrderLog orderLog = new OrderLog(1, "1", time, OrderStatus.COMPLETE);
        Long foundId = orderLog.getId();
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.FIND_ORDER_LOG_BY_FIELDS, booleanOrderLogRowMapper, foundId)).thenReturn(true);
        //when
        var result = orderDao.isThisOrderLogExist(orderLog);
        Assert.assertTrue(result == false);
    }

    @Test
    public void testFindOrderLogById() {
        //given
        OrderLog orderLog = new OrderLog();
        Long orderLogId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_LOG_BY_ID, orderLogRowMapper, orderLogId)).thenReturn(orderLog);
        //when
        var result = orderDao.findOrderLogById(orderLogId);
        Assert.assertTrue(result == null);
    }

}
