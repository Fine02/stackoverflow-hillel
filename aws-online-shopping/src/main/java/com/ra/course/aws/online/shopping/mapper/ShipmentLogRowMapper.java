package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ShipmentLogRowMapper implements RowMapper<ShipmentLog> {
    @Override
    public ShipmentLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentStatus shipmentStatus = mapToShipmentStatusForLog (rs, rowNum);
        LocalDateTime time = getLocalDate(rs, rowNum);
        ShipmentLog shipmentLog = new ShipmentLog();
        shipmentLog.setId(rs.getInt("id"));
        shipmentLog.setShipmentNumber(rs.getString("shipmentNumber"));
        shipmentLog.setStatus(shipmentStatus);
        shipmentLog.setCreationDate(time);
        return shipmentLog;
    }

    private ShipmentStatus mapToShipmentStatusForLog(ResultSet rs, int rowNum) throws SQLException {
        var status = rs.getString("status");
        return status ==null? null:ShipmentStatus.valueOf(ShipmentStatus.class, status);
    }

    private LocalDateTime getLocalDate(ResultSet rs, int i) throws SQLException {
        Timestamp ts = rs.getTimestamp("creationDate");
        return ts == null ? null : ts.toLocalDateTime();
    }
}
