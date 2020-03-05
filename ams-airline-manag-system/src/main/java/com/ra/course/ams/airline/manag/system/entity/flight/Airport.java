package com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;
import com.ra.course.ams.airline.manag.system.entity.Address;

public class Airport {

    private String name;
    private Address address;
    private String code;
    private List<Flight> flights;

    public Airport() {
    }

    private Airport(Builder builder) {
        name = builder.name;
        address = builder.address;
        code = builder.code;
        flights = builder.flights;
    }

    public static class Builder {

        private transient String name;
        private transient Address address;
        private transient String code;
        private transient List<Flight> flights;

        public Builder () {}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setFlights(List<Flight> flights) {
            this.flights = flights;
            return this;
        }

        public Airport build() {
            return new Airport(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Airport{");
        sb.append("\"name\": \"").append(name).append('"');
        sb.append(", \"address\":").append(address);
        sb.append(", \"code\": \"").append(code).append('"');
        sb.append(", \"flights\":").append(flights);
        sb.append('}');
        return sb.toString();
    }
}
