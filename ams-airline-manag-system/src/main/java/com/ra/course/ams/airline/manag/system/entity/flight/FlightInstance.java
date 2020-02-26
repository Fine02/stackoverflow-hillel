package com.ra.course.ams.airline.manag.system.entity.flight;

import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.sql.Time;
import java.util.List;

public class FlightInstance {

    private Time departureTime;
    private String gate;
    private FlightStatus status;
    private List<FlightSeat> seats;
    private Aircraft aircraft;
    private List<Crew> crews;
    private List<Pilot> pilots;

    public FlightInstance() {
    }

    private FlightInstance(Builder builder) {
        departureTime = builder.departureTime;
        gate = builder.gate;
        status = builder.status;
        seats = builder.seats;
        aircraft = builder.aircraft;
        crews = builder.crews;
        pilots = builder.pilots;
    }

    public static class Builder {

        private transient Time departureTime;
        private transient String gate;
        private transient FlightStatus status;
        private transient List<FlightSeat> seats;
        private transient Aircraft aircraft;
        private transient List<Crew> crews;
        private transient List<Pilot> pilots;

        public Builder () {}

        public Builder setDepartureTime(Time departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder setGate(String gate) {
            this.gate = gate;
            return this;
        }

        public Builder setStatus(FlightStatus status) {
            this.status = status;
            return this;
        }

        public Builder setSeats(List<FlightSeat> seats) {
            this.seats = seats;
            return this;
        }

        public Builder setAircraft(Aircraft aircraft) {
            this.aircraft = aircraft;
            return this;
        }

        public Builder setCrews(List<Crew> crews) {
            this.crews = crews;
            return this;
        }

        public Builder setPilots(List<Pilot> pilots) {
            this.pilots = pilots;
            return this;
        }

        public FlightInstance build() {
            return new FlightInstance(this);
        }
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public FlightInstance setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public String getGate() {
        return gate;
    }

    public FlightInstance setGate(String gate) {
        this.gate = gate;
        return this;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public FlightInstance setStatus(FlightStatus status) {
        this.status = status;
        return this;
    }

    public List<FlightSeat> getSeats() {
        return seats;
    }

    public FlightInstance setSeats(List<FlightSeat> seats) {
        this.seats = seats;
        return this;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public FlightInstance setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public FlightInstance setCrews(List<Crew> crews) {
        this.crews = crews;
        return this;
    }

    public List<Pilot> getPilots() {
        return pilots;
    }

    public FlightInstance setPilots(List<Pilot> pilots) {
        this.pilots = pilots;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance{");
        sb.append("\"departureTime\": \"").append(departureTime).append('"');
        sb.append(", \"gate\": \"").append(gate).append('"');
        sb.append(", \"status\":").append(status);
        sb.append(", \"seats\":").append(seats);
        sb.append(", \"aircraft\":").append(aircraft);
        sb.append(", \"crews\":").append(crews);
        sb.append(", \"pilots\":").append(pilots);
        sb.append('}');
        return sb.toString();
    }
}
