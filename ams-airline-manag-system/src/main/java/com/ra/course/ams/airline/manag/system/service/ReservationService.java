package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Itinerary;
import com.ra.course.ams.airline.manag.system.entity.Passenger;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightSeat;

import java.util.List;

public interface ReservationService {

    String reserveTicket(FlightInstance flightInstance, FlightSeat flightSeat, Passenger passenger);

    List<String> reserveMultipleTicketsByOneItinerary(Itinerary itinerary);

    void addItinerary(Itinerary itinerary);

    void addMultiFlightItinerary(Itinerary itinerary);

    void cancelReservation(String reservationNumber);

    void cancelItinerary(Itinerary itinerary);
        
}
