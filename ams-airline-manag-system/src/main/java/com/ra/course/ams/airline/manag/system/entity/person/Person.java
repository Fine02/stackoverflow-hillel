package com.ra.course.ams.airline.manag.system.entity.person;

import com.ra.course.ams.airline.manag.system.entity.Address;

public class Person {

    private String name;
    private String email;
    private String phone;
    private Address address;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    private Person(Builder builder) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
     public static class Builder {
         private String name;
         private String email;
         private String phone;
         private Address address;

         public Person build() {
             return new Person(this);
         }

         public Builder name(String name) {
             this.name = name;
             return this;
         }

         public Builder email(String email) {
             this.email = email;
             return this;
         }

         public Builder phone(String phone) {
             this.phone = phone;
             return this;
         }

         public Builder address(Address address) {
             this.address = address;
             return this;
         }
     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("\"name\": \"").append(name).append('"');
        sb.append(", \"email\": \"").append(email).append('"');
        sb.append(", \"phone\": \"").append(phone).append('"');
        sb.append(", \"address\":").append(address);
        sb.append('}');
        return sb.toString();
    }
}
