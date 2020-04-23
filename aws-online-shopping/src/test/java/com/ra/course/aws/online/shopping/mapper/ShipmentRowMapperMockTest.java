package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShipmentRowMapperMockTest {
    ShipmentRowMapper shipmentRowMapper;
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 12, 17, 27);

    @BeforeEach
    public void before() {
        shipmentRowMapper = mock(ShipmentRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        Shipment shipment = new Shipment("3",time, time,"by air");
        when((rs.getString("shipmentNumber"))).thenReturn("3");
        when((rs.getTimestamp("shipmentDate"))).thenReturn(Timestamp.valueOf(time));
        when((rs.getTimestamp("estimatedArrival"))).thenReturn(Timestamp.valueOf(time));
        when(rs.getString("shipmentMethod")).thenReturn("by air");

        Shipment result  = new ShipmentRowMapper().mapRow(rs,0);
        Assertions.assertEquals(shipment, result);
    }
}
