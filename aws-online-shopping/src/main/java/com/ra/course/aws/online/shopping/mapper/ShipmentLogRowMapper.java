package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ShipmentLogRowMapper implements RowMapper<ShipmentLog> {
    @Override
    public ShipmentLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentStatus shipmentStatus = mapToShipmentStatusForLog (rs, rowNum);
        ShipmentLog shipmentLog = new ShipmentLog();
        shipmentLog.setId(rs.getInt("id"));
        shipmentLog.setShipmentNumber(rs.getString("shipmentNumber"));
        shipmentLog.setStatus(shipmentStatus);
        shipmentLog.setCreationDate(rs.getTimestamp("creationDate").toLocalDateTime());
        return shipmentLog;
    }

    private ShipmentStatus mapToShipmentStatusForLog(ResultSet rs, int rowNum) throws SQLException {
        ShipmentStatus shipmentStatus = ShipmentStatus.valueOf(ShipmentStatus.class, rs.getString("status"));
        return shipmentStatus;
    }
}
