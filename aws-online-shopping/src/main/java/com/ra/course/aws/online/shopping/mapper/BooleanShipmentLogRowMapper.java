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
public class BooleanShipmentLogRowMapper implements RowMapper<Boolean> {
    @Override
    public Boolean mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final ShipmentLog shipmentLog = mapRowOrderLog(rs);
        if (shipmentLog != null & shipmentLog.getId()!=0) {
            return true;
        }
        return false;
    }

    private ShipmentStatus mapToShipmentStatusForLog(final ResultSet rs) throws SQLException {
        final var status = rs.getString("status");
        return status ==null? null:ShipmentStatus.valueOf(ShipmentStatus.class, status);
    }

    private LocalDateTime getLocalDate(final ResultSet rs) throws SQLException {
        final Timestamp ts = rs.getTimestamp("creationDate");
        return ts == null ? null : ts.toLocalDateTime();
    }

    public ShipmentLog mapRowOrderLog(final ResultSet rs) throws SQLException {
        final ShipmentStatus orderStatus = mapToShipmentStatusForLog(rs);
        final LocalDateTime time = getLocalDate(rs);
        final ShipmentLog shipmentLog = new ShipmentLog();
        shipmentLog.setId(rs.getInt("id"));
        shipmentLog.setShipmentNumber(rs.getString("shipmentNumber"));
        shipmentLog.setStatus(orderStatus);
        shipmentLog.setCreationDate(time);
        return shipmentLog;
    }
}
