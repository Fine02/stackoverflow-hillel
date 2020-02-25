package com.ra.course.ams.airline.manag.system.entity;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightSeat;

import java.util.HashMap;

public class FlightReservation {
    private String reservationNumber;
    private FlightInstance flight;
    private HashMap <Passenger, FlightSeat> seatMap;
    private ReservationStatus status;

    public FlightReservation(String reservationNumber, FlightInstance flight, HashMap<Passenger, FlightSeat> seatMap, ReservationStatus status) {
        this.reservationNumber = reservationNumber;
        this.flight = flight;
        this.seatMap = seatMap;
        this.status = status;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public FlightInstance getFlight() {
        return flight;
    }

    public void setFlight(FlightInstance flight) {
        this.flight = flight;
    }

    public HashMap<Passenger, FlightSeat> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(HashMap<Passenger, FlightSeat> seatMap) {
        this.seatMap = seatMap;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
