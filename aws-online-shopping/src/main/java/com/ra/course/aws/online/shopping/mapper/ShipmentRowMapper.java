package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ShipmentRowMapper implements RowMapper<Shipment> {
    @Override
    public Shipment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Shipment shipment = new Shipment();
        LocalDateTime dataShipment = getShipmentDate(rs, rowNum);
        LocalDateTime dataArrival = getEstimatedArrival(rs, rowNum);
        shipment.setShipmentNumber(rs.getString("shipmentNumber"));
        shipment.setShipmentDate(dataShipment);
        shipment.setEstimatedArrival(dataArrival);
        shipment.setShipmentMethod(rs.getString("shipmentMethod"));
        return shipment;
    }

    private LocalDateTime getShipmentDate(ResultSet rs, int i) throws SQLException {
        Timestamp ts = rs.getTimestamp("shipmentDate");
        return ts == null ? null : ts.toLocalDateTime();
    }


    private LocalDateTime getEstimatedArrival(ResultSet rs, int i) throws SQLException {
        Timestamp ts = rs.getTimestamp("estimatedArrival");
        return ts == null ? null : ts.toLocalDateTime();
    }

}
