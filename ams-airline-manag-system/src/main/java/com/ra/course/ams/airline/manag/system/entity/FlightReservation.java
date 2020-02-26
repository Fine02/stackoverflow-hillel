package com.ra.course.ams.airline.manag.system.entity;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightSeat;

import java.util.Map;

public class FlightReservation {

    private String reservationNumber;
    private Flight flight;
    private ReservationStatus status;
    private Map<Passenger, FlightSeat> seatMap;

    public FlightReservation() {
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public FlightReservation setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
        return this;
    }

    public Flight getFlight() {
        return flight;
    }

    public FlightReservation setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public FlightReservation setStatus(ReservationStatus status) {
        this.status = status;
        return this;
    }

    public Map<Passenger, FlightSeat> getSeatMap() {
        return seatMap;
    }

    public FlightReservation setSeatMap(Map<Passenger, FlightSeat> seatMap) {
        this.seatMap = seatMap;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlightReservation{");
        sb.append("\"reservationNumber\": \"").append(reservationNumber).append('"');
        sb.append(", \"flight\":").append(flight);
        sb.append(", \"status\":").append(status);
        sb.append(", \"seatMap\":").append(seatMap);
        sb.append('}');
        return sb.toString();
    }
}
