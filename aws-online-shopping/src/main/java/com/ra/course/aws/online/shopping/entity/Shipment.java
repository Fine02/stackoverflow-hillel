package com.ra.course.aws.online.shopping.entity;

import java.util.Date;

public class Shipment {
    private Date shipmentDate;
    private Date estimatedArrival;
    private String ShipmentMethod;

    public Shipment(Date shipmentDate, Date estimatedArrival, String shipmentMethod) {
        this.shipmentDate = shipmentDate;
        this.estimatedArrival = estimatedArrival;
        ShipmentMethod = shipmentMethod;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Date getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(Date estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public String getShipmentMethod() {
        return ShipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        ShipmentMethod = shipmentMethod;
    }
}
