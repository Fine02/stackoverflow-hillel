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
    private OrderDao orderDao;
    private MemberBooleanRowMapper memberRowMapper = mock(MemberBooleanRowMapper.class);
    private BooleanOrderLogRowMapper booleanLogMapper = mock(BooleanOrderLogRowMapper.class);
    private GetLastIdRowMapper getIdRowMapper = mock(GetLastIdRowMapper.class);
    private OrderRowMapper orderRowMapper = mock(OrderRowMapper.class);
    private OrderLogRowMapper orderLogRowMapper = mock(OrderLogRowMapper.class);

    private LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);

    @BeforeEach
    public void before() {
        orderDao = new JdbcOrderDaoImpl(jdbcTemplate, getIdRowMapper, orderLogRowMapper, orderRowMapper, booleanLogMapper, memberRowMapper);
    }

    @Test
    public void testUpdateOrder() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        Order order = new Order("3", OrderStatus.SHIPPED, time);
        Integer getIdOfOrder = null;
        Integer getNumberFromStatus = null;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_ID, getIdRowMapper, order.getOrderNumber())).thenReturn(getIdOfOrder);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ID_O_STATUS, getIdRowMapper, order.getStatus().toString())).thenReturn(getNumberFromStatus);
        //when
        orderDao.updateOrder(order);
        verify(jdbcTemplate).update(JdbcOrderDaoImpl.UPDATE_ORDER, getNumberFromStatus, order.getOrderNumber());
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
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ID_O_STATUS, getIdRowMapper, orderLog.getStatus().toString())).thenReturn(getNumberOfStatus);
        //when
        orderDao.addOrderLogAndUpdateOrder(order, orderLog);
        verify(jdbcTemplate).update(JdbcOrderDaoImpl.UPDATE_ORDER, getNumberOfStatus, order.getOrderNumber());
        verify(jdbcTemplate).update(JdbcOrderDaoImpl.INSERT_ORDER_LOG, orderLog.getOrderNumber(), orderLog.getCreationDate(), getNumberOfStatus, getIdOfOrder);
    }

    @Test
    public void testIsFoundMemberIDReturnFalse() {
        //given
        var memberId = 0L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_MEMBER_BY_ID, memberRowMapper, memberId)).thenReturn(false);
        //when
        var result = orderDao.isFoundMemberID(memberId);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testIsFoundMemberIDReturnTrue() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_MEMBER_BY_ID, memberRowMapper, memberId)).thenReturn(true);
        //when
        var result = orderDao.isFoundMemberID(memberId);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFindByOrderNumberAndReturnOrder() {
        //given
        var orderNumber = "1";
        List<OrderLog> list = new ArrayList<>();
        list.add(new OrderLog(1, "1", time, OrderStatus.COMPLETE));
        Order order = new Order("1", OrderStatus.SHIPPED, time);
        Order expectedResult = new Order("1", OrderStatus.SHIPPED, time);
        expectedResult.setOrderLog(list);
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG, orderLogRowMapper, orderNumber)).thenReturn(list);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER, orderRowMapper, orderNumber)).thenReturn(order);
        //when
        var actualResult = orderDao.findByOrderNumber(orderNumber);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFindByOrderNumberAndReturnNull() {
        //given
        var orderNumber = "1";
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG, orderLogRowMapper, orderNumber)).thenReturn(null);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER, orderRowMapper, orderNumber)).thenReturn(null);
        //when
        var result = orderDao.findByOrderNumber(orderNumber);
        Assert.assertTrue(result == null);
    }

    @Test
    public void testFindLogListByOrder() {
        //given
        var orderNumber = "1";
        List<OrderLog> list = new ArrayList<>();
        list.add(new OrderLog(1, "1", time, OrderStatus.COMPLETE));
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG, orderLogRowMapper, orderNumber)).thenReturn(list);
        //when
        var result = orderDao.findLogListByOrder(list);

        assertThat(result).isNotNull().isEqualTo(list);
    }

    @Test
    public void testIsThisOrderLogExistReturnTrue() {
        //given
        OrderLog orderLog = new OrderLog(1, "1", time, OrderStatus.COMPLETE);
        Long foundId = orderLog.getId();
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.FIND_ORDER_LOG, booleanLogMapper, foundId)).thenReturn(true);
        //when
        var result = orderDao.isThisOrderLogExist(orderLog);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsThisOrderLogExistReturnFalse() {
        //given
        OrderLog orderLog = new OrderLog(1, "1", time, OrderStatus.COMPLETE);
        Long foundId = orderLog.getId();
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.FIND_ORDER_LOG, booleanLogMapper, foundId)).thenReturn(false);
        //when
        var result = orderDao.isThisOrderLogExist(orderLog);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testIsThisOrderLogNullThenReturnFalse() {
        //given
        OrderLog orderLog = null;
        Long foundId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.FIND_ORDER_LOG, booleanLogMapper, foundId)).thenReturn(false);
        //when
        var result = orderDao.isThisOrderLogExist(orderLog);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testFindOrderLogById() {
        //given
        OrderLog orderLog = new OrderLog();
        Long orderLogId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_OLOG_BY_ID, orderLogRowMapper, orderLogId)).thenReturn(orderLog);
        //when
        var result = orderDao.findOrderLogById(orderLogId);
        Assert.assertEquals(orderLog, result);
    }

}
