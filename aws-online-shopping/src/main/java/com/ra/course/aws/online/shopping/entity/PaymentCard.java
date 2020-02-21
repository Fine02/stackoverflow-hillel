package com.ra.course.aws.online.shopping.entity;

import com.ra.course.aws.online.shopping.entity.enums.PaymentCardType;

public class PaymentCard {
    private PaymentCardType paymentCardType;
    private String cardNumber;
    private int dataExpireMonth;
    private int dataExpireYear;

    public PaymentCard(PaymentCardType paymentCardType, String cardNumber, int dataExpireMonth, int dataExpireYear) {
        this.paymentCardType = paymentCardType;
        this.cardNumber = cardNumber;
        this.dataExpireMonth = dataExpireMonth;
        this.dataExpireYear = dataExpireYear;
    }



    public PaymentCardType getPaymentCardType() {
        return paymentCardType;
    }

    public void setPaymentCardType(PaymentCardType paymentCardType) {
        this.paymentCardType = paymentCardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getDataExpireMonth() {
        return dataExpireMonth;
    }

    public void setDataExpireMonth(int dataExpireMonth) {
        this.dataExpireMonth = dataExpireMonth;
    }

    public int getDataExpireYear() {
        return dataExpireYear;
    }

    public void setDataExpireYear(int dataExpireYear) {
        this.dataExpireYear = dataExpireYear;
    }

}
