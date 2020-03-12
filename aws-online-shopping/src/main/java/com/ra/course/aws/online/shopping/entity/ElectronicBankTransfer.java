package com.ra.course.aws.online.shopping.entity;

public class ElectronicBankTransfer {
    String bankName;
    String routingNumber;
    String accountNumber;
    public String getBankName(){
        return bankName;
    }
    public String getRoutingNumber(){
        return routingNumber;
    }
    public String getAccountNumber(){
        return accountNumber;
    }
    public void setBankName(String bankName){
        this.bankName = bankName;
    }
    public void setRoutingNumber(String routingNumber){
        this.routingNumber = routingNumber;

    }
    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }
}
