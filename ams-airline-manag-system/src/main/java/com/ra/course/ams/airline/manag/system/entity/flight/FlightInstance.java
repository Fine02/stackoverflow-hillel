package com.ra.course.ams.airline.manag.system.entity.flight;

import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.sql.Time;
import java.util.List;

public class FlightInstance {

    private String id;
    private Flight flight;
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
        id = builder.id;
        flight = builder.flight;
        departureTime = builder.departureTime;
        gate = builder.gate;
        status = builder.status;
        seats = builder.seats;
        aircraft = builder.aircraft;
        crews = builder.crews;
        pilots = builder.pilots;
    }

    public static class Builder {

        private transient String id;
        private transient Flight flight;
        private transient Time departureTime;
        private transient String gate;
        private transient FlightStatus status;
        private transient List<FlightSeat> seats;
        private transient Aircraft aircraft;
        private transient List<Crew> crews;
        private transient List<Pilot> pilots;

        public Builder () {}

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFlight(Flight flight) {
            this.flight = flight;
            return this;
        }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public List<FlightSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<FlightSeat> seats) {
        this.seats = seats;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }

    public List<Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(List<Pilot> pilots) {
        this.pilots = pilots;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlightInstance{");
        sb.append("\"id\": \"").append(id).append('"');
        sb.append(", \"flight\":").append(flight);
        sb.append(", \"departureTime\": \"").append(departureTime).append('"');
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
