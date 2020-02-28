package com.ra.course.aws.online.shopping.entity;

public class ElectronicBankTransfer {
    private String bankName;
    private String routingNumber;
    private String accountNumber;

    public ElectronicBankTransfer(String bankName, String routingNumber, String accountNumber) {
        this.bankName = bankName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
