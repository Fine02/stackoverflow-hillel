package com.ra.course.aws.online.shopping.entity;

public class CreditCard {
    private String nameOnCard;
    private int cardNumber;
    private int code;
    private Address billingAddress;

    public CreditCard(String nameOnCard, int cardNumber, int code, Address billingAddress) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.code = code;
        this.billingAddress = billingAddress;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}

