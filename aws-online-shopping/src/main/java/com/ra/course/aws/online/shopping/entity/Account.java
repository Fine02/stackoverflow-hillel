package com.ra.course.aws.online.shopping.entity;

import com.ra.course.aws.online.shopping.entity.enums.Gender;
import com.ra.course.aws.online.shopping.entity.interfaces.Address;

import java.util.List;

public class Account {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Gender gender;
    List <PhoneNumber> phoneNumbers;
    List<Address> addresses;
    private PaymentCard card;

    public Account() {
    }

    public Account(Long id, String name, String lastName, String email, Gender gender, List<PhoneNumber> phoneNumbers, List<Address> addresses, PaymentCard Card) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phoneNumbers = phoneNumbers;
        this.addresses = addresses;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public PaymentCard getCard() {
        return card;
    }

    public void setCard(PaymentCard card) {
        this.card = card;
    }


}
