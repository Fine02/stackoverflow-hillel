package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanOrderLogRowMapperMockTest {
    BooleanOrderLogRowMapper booleanOrderLogRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        booleanOrderLogRowMapper = mock(BooleanOrderLogRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("id")).thenReturn(1);
        when((rs.getString("orderNumber"))).thenReturn("3");
        when(rs.getTimestamp("creationDate")).thenReturn(Timestamp.valueOf(time));
        when(rs.getString("status")).thenReturn(String.valueOf(OrderStatus.CANCELED));

        Boolean result = new BooleanOrderLogRowMapper().mapRow(rs, 0);
        assertEquals(true, result);
    }
}
