package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderRowMapperMockTest {
    OrderRowMapper orderRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        orderRowMapper = mock(OrderRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        Order order = new Order("3",OrderStatus.COMPLETE,time);
        when((rs.getString("orderNumber"))).thenReturn("3");
        when(rs.getString("status")).thenReturn(String.valueOf(OrderStatus.COMPLETE));
        when(rs.getTimestamp("orderDate")).thenReturn(Timestamp.valueOf(time));

        Order result  = new OrderRowMapper().mapRow(rs,0);
        Assertions.assertEquals(order, result);
    }

    @Test
    public void testWithNullValueMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        Order order = new Order();
        when((rs.getString("orderNumber"))).thenReturn(null);
        when(rs.getString("status")).thenReturn(null);
        when(rs.getTimestamp("orderDate")).thenReturn(null);

        Order result  = new OrderRowMapper().mapRow(rs,0);
        Assertions.assertEquals(order, result);
    }
}
