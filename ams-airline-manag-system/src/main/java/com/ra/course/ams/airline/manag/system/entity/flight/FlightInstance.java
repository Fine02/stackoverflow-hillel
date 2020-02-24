package com.ra.course.ams.airline.manag.system.entity.flight;

import java.sql.Time;
import java.util.List;

public class FlightInstance {

    private Time departureTime;
    private String gate;
    private FlightStatus status;
    private List<FlightSeat> seats;
    private Aircraft aircraft;
    // TODO resolve dependency
    private List<Crew> crews;
    // TODO resolve dependency
    private List<Pilot> pilots;

    // TODO implementation is not clear
    private boolean cancel() {
        return false;
    }

    private void updateStatus(FlightStatus status) {
        this.status = status;
    }

    public FlightInstance() {
    }

    public FlightInstance(Time departureTime, String gate, FlightStatus status, List<FlightSeat> seats, Aircraft aircraft, List<Crew> crews, List<Pilot> pilots) {
        this.departureTime = departureTime;
        this.gate = gate;
        this.status = status;
        this.seats = seats;
        this.aircraft = aircraft;
        this.crews = crews;
        this.pilots = pilots;
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
