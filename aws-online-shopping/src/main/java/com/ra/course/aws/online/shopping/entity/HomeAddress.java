package com.ra.course.aws.online.shopping.entity;

import com.ra.course.aws.online.shopping.entity.interfaces.Address;

public class HomeAddress implements Address {
    private Long id;
    private int zipCode;
    private String State;
    private String City;
    private String streetAddress;

    public HomeAddress(Long id, int zipCode, String state, String city, String streetAddress) {
        this.id = id;
        this.zipCode = zipCode;
        State = state;
        City = city;
        this.streetAddress = streetAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
}
