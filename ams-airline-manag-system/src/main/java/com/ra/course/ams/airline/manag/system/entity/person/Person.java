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
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
    }
     public static class Builder {
         private transient String name;
         private transient String email;
         private transient String phone;
         private transient Address address;

         public Person build() {
             return new Person(this);
         }

         public Builder setName(String name) {
             this.name = name;
             return this;
         }

         public Builder setEmail(String email) {
             this.email = email;
             return this;
         }

         public Builder setPhone(String phone) {
             this.phone = phone;
             return this;
         }

         public Builder setAddress(Address address) {
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
