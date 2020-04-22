package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ShipmentRowMapper implements RowMapper<Shipment> {
    @Override
    public Shipment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Shipment shipment = new Shipment();
        shipment.setShipmentNumber(rs.getString("shipmentNumber"));
        shipment.setShipmentDate(rs.getTimestamp("shipmentDate").toLocalDateTime());
        shipment.setEstimatedArrival(rs.getTimestamp("estimatedArrival").toLocalDateTime());
        shipment.setShipmentMethod(rs.getString("shipmentMethod"));
        return shipment;
    }
}
