package com.ra.course.aws.online.shopping.entity.payment;
import com.ra.course.aws.online.shopping.entity.Address;

import java.util.Objects;

public class CreditCard {
    private String nameOnCard;
    private String cardNumber;
    private int code;
    private Address billingAddress;

    public CreditCard() {
    }

    public CreditCard(String nameOnCard, String cardNumber, int code, Address billingAddress) {
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return code == that.code &&
                Objects.equals(nameOnCard, that.nameOnCard) &&
                Objects.equals(cardNumber, that.cardNumber) &&
                Objects.equals(billingAddress, that.billingAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOnCard, cardNumber, code, billingAddress);
    }
}

