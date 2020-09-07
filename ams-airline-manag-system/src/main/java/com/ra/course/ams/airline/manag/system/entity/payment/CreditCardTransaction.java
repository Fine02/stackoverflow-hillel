package com.ra.course.ams.airline.manag.system.entity.payment;

public class CreditCardTransaction extends Payment {
    private String nameOnCard;

    public CreditCardTransaction(int paimentId, double amound, PaymentStatus staus, String nameOnCard) {
        super(paimentId, amound, staus);
        this.nameOnCard = nameOnCard;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
}
