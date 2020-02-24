package com.ra.course.ams.airline.manag.system.entity.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.Payment;

public class CashTransaction extends Payment {
    private double cashTendered;

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
