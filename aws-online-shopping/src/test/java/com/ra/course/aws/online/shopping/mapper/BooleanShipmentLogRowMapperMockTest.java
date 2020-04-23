package com.ra.course.aws.online.shopping.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanShipmentLogRowMapperMockTest {
    BooleanShipmentLogRowMapper booleanShipmentLogRowMapper;

    @BeforeEach
    public void before() {
        booleanShipmentLogRowMapper = mock(BooleanShipmentLogRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);

        when(rs.getInt("id")).thenReturn(1);
        when((rs.getString("shipmentNumber"))).thenReturn("3");
        when(rs.getString("status")).thenReturn(null);
        when(rs.getTimestamp("creationDate")).thenReturn(null);

        Boolean result  = new BooleanShipmentLogRowMapper().mapRow(rs,0);
        assertEquals(true, result);
    }
}
