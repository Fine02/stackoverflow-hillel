package com.ra.course.ams.airline.manag.system.entity.payment;

public class Payment {
    private int paimentId;
    private double amound;
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(int paimentId, double amound, PaymentStatus staus) {
        this.paimentId = paimentId;
        this.amound = amound;
        this.status = staus;
    }

    public int getPaimentId() {
        return paimentId;
    }

    public void setPaimentId(int paimentId) {
        this.paimentId = paimentId;
    }

    public double getAmound() {
        return amound;
    }

    public void setAmound(double amound) {
        this.amound = amound;
    }

    public PaymentStatus getStaus() {
        return status;
    }

    public void setStaus(PaymentStatus staus) {
        this.status = staus;
    }
}
