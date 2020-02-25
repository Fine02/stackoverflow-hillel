package com.ra.course.ams.airline.manag.system.entity;

import com.ra.course.ams.airline.manag.system.entity.flight.Airport;

import java.util.Date;
import java.util.List;

public class Itinerary {

    private Airport startingAirport;
    private Airport finalAirport;
    private Date creationDate;
    private List<Passenger> passengers;
    private List<FlightReservation> flightReservations;

    public Itinerary() {
    }

    public Airport getStartingAirport() {
        return startingAirport;
    }

    public Itinerary setStartingAirport(Airport startingAirport) {
        this.startingAirport = startingAirport;
        return this;
    }

    public Airport getFinalAirport() {
        return finalAirport;
    }

    public Itinerary setFinalAirport(Airport finalAirport) {
        this.finalAirport = finalAirport;
        return this;
    }

    public Itinerary(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<FlightReservation> getFlightReservations() {
        return flightReservations;
    }

    public void setFlightReservations(List<FlightReservation> flightReservations) {
        this.flightReservations = flightReservations;
    }
}
