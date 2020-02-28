package com.ra.course.ams.airline.manag.system.entity.flight;

import java.util.List;

public class Aircraft {

    private String id;

    private String name;
    private String modal;
    private int manufacturingYear;
    private FlightInstance flightInstance;
    private List<Seat> seats;
    private List<Flight> flights;

    public Aircraft() {
    }

    private Aircraft(Builder builder) {
        id = builder.id;
        name = builder.name;
        modal = builder.modal;
        manufacturingYear = builder.manufacturingYear;
        flightInstance = builder.flightInstance;
        seats = builder.seats;
        flights = builder.flights;
    }

    public static class Builder {

        private transient String id;
        private transient String name;
        private transient String modal;
        private transient int manufacturingYear;
        private transient FlightInstance flightInstance;
        private transient List<Seat> seats;
        private transient List<Flight> flights;

        public Builder () {}

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

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

    public String getId() {
        return id;
    }

    public Aircraft setId(String id) {
        this.id = id;
        return this;
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
        final StringBuffer sb = new StringBuffer("Aircraft{");
        sb.append("\"id\": \"").append(id).append('"');
        sb.append(", \"name\": \"").append(name).append('"');
        sb.append(", \"modal\": \"").append(modal).append('"');
        sb.append(", \"manufacturingYear\":").append(manufacturingYear);
        sb.append(", \"flightInstance\":").append(flightInstance);
        sb.append(", \"seats\":").append(seats);
        sb.append(", \"flights\":").append(flights);
        sb.append('}');
        return sb.toString();
    }
}
