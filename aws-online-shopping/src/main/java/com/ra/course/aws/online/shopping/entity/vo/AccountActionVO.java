package com.ra.course.aws.online.shopping.entity.vo;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;

import java.util.List;
import java.util.Objects;

public class AccountActionVO {
    private Long id;
    private String userName;
    private String password;
    private AccountStatus status;
    private String name;

    private Long addressId;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private String email;
    private String phone;

    private String nameOnCard;
    private String cardNumber;
    private int code;

    private Long billingAddressId;
    private String billingStreetAddress;
    private String billingCity;
    private String billingState;
    private String billingZipCode;
    private String billingCountry;

    private String bankName;
    private String routingNumber;
    private String accountNumber;

    public AccountActionVO() {
    }

    public AccountActionVO(Long id, String userName, String password, AccountStatus status, String name, Long addressId,
                           String streetAddress, String city, String state, String zipCode, String country, String email,
                           String phone, String nameOnCard, String cardNumber, int code, Long billingAddressId,
                           String billingStreetAddress, String billingCity, String billingState, String billingZipCode,
                           String billingCountry, String bankName, String routingNumber, String accountNumber) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
        this.addressId = addressId;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.code = code;
        this.billingAddressId = billingAddressId;
        this.billingStreetAddress = billingStreetAddress;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZipCode = billingZipCode;
        this.billingCountry = billingCountry;
        this.bankName = bankName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZipCode() {
        return billingZipCode;
    }

    public void setBillingZipCode(String billingZipCode) {
        this.billingZipCode = billingZipCode;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
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
        if (!(o instanceof AccountActionVO)) return false;
        AccountActionVO that = (AccountActionVO) o;
        return getCode() == that.getCode() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAddressId(), that.getAddressId()) &&
                Objects.equals(getStreetAddress(), that.getStreetAddress()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getZipCode(), that.getZipCode()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getNameOnCard(), that.getNameOnCard()) &&
                Objects.equals(getCardNumber(), that.getCardNumber()) &&
                Objects.equals(getBillingAddressId(), that.getBillingAddressId()) &&
                Objects.equals(getBillingStreetAddress(), that.getBillingStreetAddress()) &&
                Objects.equals(getBillingCity(), that.getBillingCity()) &&
                Objects.equals(getBillingState(), that.getBillingState()) &&
                Objects.equals(getBillingZipCode(), that.getBillingZipCode()) &&
                Objects.equals(getBillingCountry(), that.getBillingCountry()) &&
                Objects.equals(getBankName(), that.getBankName()) &&
                Objects.equals(getRoutingNumber(), that.getRoutingNumber()) &&
                Objects.equals(getAccountNumber(), that.getAccountNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName());
    }
}
