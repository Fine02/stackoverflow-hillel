package com.ra.course.aws.online.shopping.entity.shipment;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShipmentLog {
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
}
