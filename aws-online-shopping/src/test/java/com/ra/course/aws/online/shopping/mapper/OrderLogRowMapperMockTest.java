package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderLogRowMapperMockTest {
    OrderLogRowMapper orderLogRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        orderLogRowMapper = mock(OrderLogRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        OrderLog orderLog = new OrderLog(1,"3",time,OrderStatus.COMPLETE);
        when(rs.getInt("id")).thenReturn(1);
        when((rs.getString("orderNumber"))).thenReturn("3");
        when(rs.getTimestamp("creationDate")).thenReturn(Timestamp.valueOf(time));
        when(rs.getString("status")).thenReturn(String.valueOf(OrderStatus.COMPLETE));

        OrderLog result  = new OrderLogRowMapper().mapRow(rs,0);
        Assertions.assertEquals(orderLog, result);
    }
}
