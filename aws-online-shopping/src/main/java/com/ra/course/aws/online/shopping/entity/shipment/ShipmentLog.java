package com.ra.course.aws.online.shopping.entity.shipment;
import java.time.LocalDate;

public class ShipmentLog {
    private String shipmentNumber;
    private ShipmentStatus status;
    private LocalDate creationDate;

    public ShipmentLog() {
    }

    public ShipmentLog(ShipmentStatus status, LocalDate creationDate) {
        this.status = status;
        this.creationDate = creationDate;
    }

    public ShipmentLog(String shipmentNumber, ShipmentStatus status, LocalDate creationDate) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
