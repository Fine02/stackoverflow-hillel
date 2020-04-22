package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcOrderDaoImpl;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.mapper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        orderDao =new JdbcOrderDaoImpl(jdbcTemplate,memberBooleanRowMapper,booleanOrderLogRowMapper, getIdRowMapper, orderRowMapper, orderLogRowMapper);
        memberBooleanRowMapper =mock(MemberBooleanRowMapper.class);
        booleanOrderLogRowMapper =mock(BooleanOrderLogRowMapper.class);
        getIdRowMapper =mock(GetLastIdRowMapper.class);
        orderRowMapper =mock(OrderRowMapper.class);
        orderLogRowMapper =mock(OrderLogRowMapper.class);
    }

    @Test
    public void testUpdateOrder() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        Order order = new Order("3", OrderStatus.SHIPPED, time);
        //when
        orderDao.updateOrder(order);
        //verify(orderDao).updateOrder(order); -don't work
    }

    @Test
    public void testAddOrderLogAndUpdateOrder() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        OrderLog orderLog = new OrderLog("6", time, OrderStatus.COMPLETE);
        Order order = new Order("6", OrderStatus.SHIPPED, time);
        //when
        orderDao.addOrderLogAndUpdateOrder(order,orderLog);

    }

    @Test
    public void testIsFoundMemberID() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_MEMBER_BY_ID,  memberBooleanRowMapper, memberId)).thenReturn(false);
        //when
        orderDao.isFoundMemberID(memberId);
    }

    @Test
    public void testFindByOrderNumber() {
        //given
        var orderNumber = "1";
        List<OrderLog> list = new ArrayList<>();
        list.add(new OrderLog(1,"1", time, OrderStatus.COMPLETE));
        Order order = new Order("1", OrderStatus.SHIPPED, time);
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG_BY_ORDER_NUMBER, orderLogRowMapper, orderNumber)).thenReturn(list);
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_BY_ORDER_NUMBER, orderRowMapper, orderNumber)).thenReturn(order);
        //when
       var result = orderDao.findByOrderNumber(orderNumber);
        System.out.println(result);
    }

    @Test
    public void testFindLogListByOrder() {
        //given
        var orderNumber = "1";
        List<OrderLog> list = new ArrayList<>();
        list.add(new OrderLog(1,"1", time, OrderStatus.COMPLETE));
        when(jdbcTemplate.query(JdbcOrderDaoImpl.GET_ORDER_LOG_BY_ORDER_NUMBER, orderLogRowMapper, orderNumber)).thenReturn(list);
        //when
        var result = orderDao.findLogListByOrder(list);
        System.out.println(result);
    }

    @Test
    public void testIsThisOrderLogExist() {
        //given
        OrderLog orderLog = new OrderLog(1,"1", time, OrderStatus.COMPLETE);
        Long foundId = orderLog.getId();
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.FIND_ORDER_LOG_BY_FIELDS, booleanOrderLogRowMapper, foundId)).thenReturn(true);
        //when
        var result = orderDao.isThisOrderLogExist(orderLog);
        System.out.println(result);
    }

    @Test
    public void testFindOrderLogById() {
        //given
        OrderLog orderLog = new OrderLog();
        Long orderLogId = 1L;
        when(jdbcTemplate.queryForObject(JdbcOrderDaoImpl.GET_ORDER_LOG_BY_ID, orderLogRowMapper, orderLogId)).thenReturn(orderLog);
        //when
        var result = orderDao.findOrderLogById(orderLogId);
        System.out.println(result);
    }

}
