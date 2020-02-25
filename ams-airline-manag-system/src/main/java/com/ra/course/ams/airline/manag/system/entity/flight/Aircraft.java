package com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;

public class Aircraft {

    private String name;
    private String modal;
    private int manufacturingYear;
    private FlightInstance flightInstance;
    private List<Seat> seats;
    private List<Flight> flights;

    public Aircraft() {
    }

    public Aircraft(String name, String modal, int manufacturingYear, FlightInstance flightInstance, List<Seat> seats, List<Flight> flights) {
        this.name = name;
        this.modal = modal;
        this.manufacturingYear = manufacturingYear;
        this.flightInstance = flightInstance;
        this.seats = seats;
        this.flights = flights;
    }

    private Aircraft(Builder builder) {
        name = builder.name;
        modal = builder.modal;
        manufacturingYear = builder.manufacturingYear;
        flightInstance = builder.flightInstance;
        seats = builder.seats;
        flights = builder.flights;
    }

    public static class Builder {

        private String name;
        private String modal;
        private int manufacturingYear;
        private FlightInstance flightInstance;
        private List<Seat> seats;
        private List<Flight> flights;

        public Builder () {}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setModal(String modal) {
            this.modal = modal;
            return this;
        }

        public Builder setManufacturingYear(int manufacturingYear) {
            this.manufacturingYear = manufacturingYear;
            return this;
        }

        public Builder setFlightInstance(FlightInstance flightInstance) {
            this.flightInstance = flightInstance;
            return this;
        }

        public Builder setSeats(List<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public Builder setFlights(List<Flight> flights) {
            this.flights = flights;
            return this;
        }

        public Aircraft build() {
            return new Aircraft(this);
        }
    }

    public String getName() {
        return name;
    }

    public Aircraft setName(String name) {
        this.name = name;
        return this;
    }

    public String getModal() {
        return modal;
    }

    public Aircraft setModal(String modal) {
        this.modal = modal;
        return this;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public Aircraft setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
        return this;
    }

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public Aircraft setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
        return this;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Aircraft setSeats(List<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Aircraft setFlights(List<Flight> flights) {
        this.flights = flights;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.ra.course.ams.airline.manag.system.entity.flight.Aircraft{");
        sb.append("\"name\": \"").append(name).append('"');
        sb.append(", \"modal\": \"").append(modal).append('"');
        sb.append(", \"manufacturingYear\":").append(manufacturingYear);
        sb.append(", \"flightInstance\":").append(flightInstance);
        sb.append(", \"seats\":").append(seats);
        sb.append(", \"flights\":").append(flights);
        sb.append('}');
        return sb.toString();
    }
}
