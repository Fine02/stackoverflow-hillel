package com.ra.course.ams.airline.manag.system.entity;

public class CreditCardTransaction extends Payment {
    private String nameOnCard;

    public CreditCardTransaction(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
}
