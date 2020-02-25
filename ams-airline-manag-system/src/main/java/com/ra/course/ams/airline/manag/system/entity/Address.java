package com.ra.course.ams.airline.manag.system.entity;

public class Address {

    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    public Address() {
    }

    private Address(Builder builder) {
        this.streetAddress = builder.streetAddress;
        this.city = builder.city;
        this.state = builder.state;
        this.zipcode = builder.zipcode;
        this.country = builder.country;
    }

    public static class Builder {
        private transient String streetAddress;
        private transient String city;
        private transient String state;
        private transient String zipcode;
        private transient String country;
        public Address build() {
            return new Address(this);
        }

        public Builder(String city, String country) {
            this.city = city;
            this.country = country;
        }

        public Builder setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setZipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("\"streetAddress\": \"").append(streetAddress).append('"');
        sb.append(", \"city\": \"").append(city).append('"');
        sb.append(", \"state\": \"").append(state).append('"');
        sb.append(", \"zipcode\": \"").append(zipcode).append('"');
        sb.append(", \"country\": \"").append(country).append('"');
        sb.append('}');
        return sb.toString();
    }

}
