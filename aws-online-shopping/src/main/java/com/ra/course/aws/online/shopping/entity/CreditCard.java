package com.ra.course.aws.online.shopping.entity;

public class CreditCard {
    String nameOnCard;
    String cardNumber;
    Integer code;
    Address billingAddress;

    public String getNameOnCard(){
        return nameOnCard;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public Integer getCode(){
        return code;
    }

    public Address getBillingAddress(){
        return billingAddress;
    }
    public void setNameOnCard(String nameOnCard){
        this.nameOnCard = nameOnCard;

    }
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;

    }
    public void setCode(Integer code){
        this.code = code;
    }
    public void  setBillingAddress (Address billingAddress){
        this.billingAddress = billingAddress;

    }
}

