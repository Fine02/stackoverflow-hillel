package com.ra.course.aws.online.shopping.entity;

import java.time.LocalDate;
import java.util.Date;

public class ShipmentLog {
    private ShipmentStatus status;
    private LocalDate creationDate;

    public ShipmentLog(ShipmentStatus status, LocalDate creationDate) {
        this.status = status;
        this.creationDate = creationDate;
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
