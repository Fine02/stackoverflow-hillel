package com.ra.course.ams.airline.manag.system.entity.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.Payment;

public class CheckTransaction extends Payment {
    private String bankName;
    private String checkNumber;

    public CheckTransaction(String bankName, String checkNumber) {
        this.bankName = bankName;
        this.checkNumber = checkNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }
}
