package com.ra.course.ams.airline.manag.system.entity.payment;

public class CashTransaction extends Payment {
    private double cashTendered;

    public CashTransaction(int paimentId, double amound, PaymentStatus staus, double cashTendered) {
        super(paimentId, amound, staus);
        this.cashTendered = cashTendered;
    }

    public CashTransaction(double cashTendered) {
        this.cashTendered = cashTendered;
    }

    public double getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(double cashTendered) {
        this.cashTendered = cashTendered;
    }
}
