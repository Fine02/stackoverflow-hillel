package com.ra.course.aws.online.shopping.entity;

import java.util.Date;

public class ShipmentLog {
    private ShipmentStatus status;
    private Date creationDate;

    public ShipmentLog(ShipmentStatus status, Date creationDate) {
        this.status = status;
        this.creationDate = creationDate;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
