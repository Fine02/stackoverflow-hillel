package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShipmentLogRowMapperMockTest {
    ShipmentLogRowMapper shipmentLogRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        shipmentLogRowMapper = mock(ShipmentLogRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        ShipmentLog shipmentLog = new ShipmentLog(1,"3",ShipmentStatus.ONHOLD,time);
        when(rs.getInt("id")).thenReturn(1);
        when((rs.getString("shipmentNumber"))).thenReturn("3");
        when(rs.getString("status")).thenReturn(String.valueOf(ShipmentStatus.ONHOLD));
        when(rs.getTimestamp("creationDate")).thenReturn(Timestamp.valueOf(time));

        ShipmentLog result = new ShipmentLogRowMapper().mapRow(rs,0);
        Assertions.assertEquals(shipmentLog, result);
    }

}
