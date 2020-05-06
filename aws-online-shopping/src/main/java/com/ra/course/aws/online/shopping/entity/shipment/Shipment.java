package com.ra.course.aws.online.shopping.entity.shipment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Shipment {
    private String shipmentNumber;
    private LocalDateTime shipmentDate;
    private LocalDateTime estimatedArrival;
    private String ShipmentMethod;
    private List <ShipmentLog> shipmentLogs;

    public Shipment() {
    }

    public Shipment(String shipmentNumber, LocalDateTime shipmentDate, LocalDateTime estimatedArrival, String shipmentMethod) {
        this.shipmentNumber = shipmentNumber;
        this.shipmentDate = shipmentDate;
        this.estimatedArrival = estimatedArrival;
        ShipmentMethod = shipmentMethod;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(shipmentNumber, shipment.shipmentNumber) &&
                Objects.equals(shipmentDate, shipment.shipmentDate) &&
                Objects.equals(estimatedArrival, shipment.estimatedArrival) &&
                Objects.equals(ShipmentMethod, shipment.ShipmentMethod) &&
                Objects.equals(shipmentLogs, shipment.shipmentLogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentNumber, shipmentDate, estimatedArrival, ShipmentMethod, shipmentLogs);
    }
}
