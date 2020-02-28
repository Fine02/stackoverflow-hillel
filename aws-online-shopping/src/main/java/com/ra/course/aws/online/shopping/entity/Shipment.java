package com.ra.course.aws.online.shopping.entity;

import java.time.LocalDate;

public class Shipment {
    private LocalDate shipmentDate;
    private LocalDate estimatedArrival;
    private String ShipmentMethod;

    public Shipment(LocalDate shipmentDate, LocalDate estimatedArrival, String shipmentMethod) {
        this.shipmentDate = shipmentDate;
        this.estimatedArrival = estimatedArrival;
        ShipmentMethod = shipmentMethod;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public LocalDate getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(LocalDate estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public String getShipmentMethod() {
        return ShipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        ShipmentMethod = shipmentMethod;
    }
}
