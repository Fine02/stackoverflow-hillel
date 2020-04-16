package com.ra.course.aws.online.shopping.entity.shipment;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class ShipmentLog {
    private long id;
    private String shipmentNumber;
    private ShipmentStatus status;
    private LocalDateTime creationDate;

    public ShipmentLog() {
    }

    public ShipmentLog(String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        this.shipmentNumber = shipmentNumber;
        this.status = status;
        this.creationDate = creationDate;
    }

    public ShipmentLog(long id, String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        this.id = id;
        this.shipmentNumber = shipmentNumber;
        this.status = status;
        this.creationDate = creationDate;
    }

    public ShipmentLog(String orderNumber) {
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentLog that = (ShipmentLog) o;
        return id == that.id &&
                Objects.equals(shipmentNumber, that.shipmentNumber) &&
                status == that.status &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipmentNumber, status, creationDate);
    }
}
