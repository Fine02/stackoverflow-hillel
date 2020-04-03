package com.ra.course.aws.online.shopping.entity.shipment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Shipment {
    private String shipmentNumber;
    private LocalDateTime shipmentDate;
    private LocalDateTime estimatedArrival;
    private String ShipmentMethod;
    private List <ShipmentLog> shipmentLogs;

    public Shipment() {
    }

    public Shipment(LocalDateTime shipmentDate, LocalDateTime estimatedArrival, String shipmentMethod, List<ShipmentLog> shipmentLogs) {
        this.shipmentDate = shipmentDate;
        this.estimatedArrival = estimatedArrival;
        ShipmentMethod = shipmentMethod;
        this.shipmentLogs = shipmentLogs;
    }

    public Shipment(String shipmentNumber, LocalDateTime shipmentDate, LocalDateTime estimatedArrival, String shipmentMethod, List<ShipmentLog> shipmentLogs) {
        this.shipmentNumber = shipmentNumber;
        this.shipmentDate = shipmentDate;
        this.estimatedArrival = estimatedArrival;
        ShipmentMethod = shipmentMethod;
        this.shipmentLogs = shipmentLogs;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public LocalDateTime getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(LocalDateTime estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public String getShipmentMethod() {
        return ShipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        ShipmentMethod = shipmentMethod;
    }

    public List<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    public void setShipmentLogs(List<ShipmentLog> shipmentLogs) {
        this.shipmentLogs = shipmentLogs;
    }
}
