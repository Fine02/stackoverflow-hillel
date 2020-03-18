package com.ra.course.aws.online.shopping.entity.payment;

import java.util.Objects;

public class ElectronicBankTransfer {
    private String bankName;
    private String routingNumber;
    private String accountNumber;

    public ElectronicBankTransfer() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectronicBankTransfer that = (ElectronicBankTransfer) o;
        return Objects.equals(bankName, that.bankName) &&
                Objects.equals(routingNumber, that.routingNumber) &&
                Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankName, routingNumber, accountNumber);
    }
}
