package com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;

public class Airline {

    private String name;
    private String code;
    private List<Flight> flights;
    private List<Aircraft> aircrafts;

    public Airline() {
    }

    private Airline(Builder builder) {
        name = builder.name;
        code = builder.code;
        flights = builder.flights;
        aircrafts = builder.aircrafts;
    }

    public static class Builder {

        private transient String name;
        private transient String code;
        private transient List<Flight> flights;
        private transient List<Aircraft> aircrafts;

        public Builder () {}

        public Builder setName(String name) {
            this.name = name;
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

        public Builder setAircrafts(List<Aircraft> aircrafts) {
            this.aircrafts = aircrafts;
            return this;
        }

        public Airline build() {
            return new Airline(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Airline{");
        sb.append("\"name\": \"").append(name).append('"');
        sb.append(", \"code\": \"").append(code).append('"');
        sb.append(", \"flights\":").append(flights);
        sb.append(", \"aircrafts\":").append(aircrafts);
        sb.append('}');
        return sb.toString();
    }
}
