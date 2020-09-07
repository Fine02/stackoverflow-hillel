package com.ra.course.aws.online.shopping.entity.user;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Account {
    private Long id;
    private String userName;
    private String password;
    private AccountStatus status;
    private String name;
    private Address shippingAddress;
    private String email;
    private String phone;
    private List<CreditCard> creditCardList;
    private List<ElectronicBankTransfer> electronicBankTransferList;

    public Account() {
    }

    public Account(Long id, String userName, String password, AccountStatus status, String name, Address shippingAddress, String email, String phone, List<CreditCard> creditCardList, List<ElectronicBankTransfer> electronicBankTransferList) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
        this.shippingAddress = shippingAddress;
        this.email = email;
        this.phone = phone;
        this.creditCardList = creditCardList;
        this.electronicBankTransferList = electronicBankTransferList;
    }

    public Account(String userName, String password, AccountStatus status, String name, Address shippingAddress, String email, String phone, List<CreditCard> creditCardList, List<ElectronicBankTransfer> electronicBankTransferList) {
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
        this.shippingAddress = shippingAddress;
        this.email = email;
        this.phone = phone;
        this.creditCardList = creditCardList;
        this.electronicBankTransferList = electronicBankTransferList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CreditCard> getCreditCardList() {
        return creditCardList;
    }

    public void setCreditCardList(List<CreditCard> creditCardList) {
        this.creditCardList = creditCardList;
    }

    public List<ElectronicBankTransfer> getElectronicBankTransferList() {
        return electronicBankTransferList;
    }

    public void setElectronicBankTransferList(List<ElectronicBankTransfer> electronicBankTransferList) {
        this.electronicBankTransferList = electronicBankTransferList;
    }
}
