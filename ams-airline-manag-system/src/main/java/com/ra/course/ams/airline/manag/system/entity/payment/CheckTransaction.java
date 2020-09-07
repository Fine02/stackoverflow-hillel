package com.ra.course.ams.airline.manag.system.entity.payment;

public class CheckTransaction extends Payment {
    private String bankName;
    private String checkNumber;

    public CheckTransaction(int paimentId, double amound, PaymentStatus staus, String bankName, String checkNumber) {
        super(paimentId, amound, staus);
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
