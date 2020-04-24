package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanShipmentLogRowMapperMockTest {
    BooleanShipmentLogRowMapper booleanShipmentLogRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        booleanShipmentLogRowMapper = mock(BooleanShipmentLogRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);

        when(rs.getInt("id")).thenReturn(1);
        when((rs.getString("shipmentNumber"))).thenReturn("3");
        when(rs.getString("status")).thenReturn(String.valueOf(ShipmentStatus.DELIVERED));
        when(rs.getTimestamp("creationDate")).thenReturn(Timestamp.valueOf(time));

        Boolean result  = new BooleanShipmentLogRowMapper().mapRow(rs,0);
        assertEquals(true, result);
    }

    @Test
    public void testReturnFalseMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);

        when(rs.getInt("id")).thenReturn(0);
        when((rs.getString("shipmentNumber"))).thenReturn(null);
        when(rs.getString("status")).thenReturn(null);
        when(rs.getTimestamp("creationDate")).thenReturn(null);

        Boolean result  = new BooleanShipmentLogRowMapper().mapRow(rs,0);
        assertEquals(false, result);
    }
}
