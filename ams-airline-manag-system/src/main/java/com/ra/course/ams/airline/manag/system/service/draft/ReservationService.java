package com.ra.course.ams.airline.manag.system.service.draft;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.Itinerary;
import com.ra.course.ams.airline.manag.system.entity.person.Customer;

import java.util.List;

public interface ReservationService {

    FlightReservation create(FlightReservation reservation);
    void cancel(FlightReservation reservation);
    Itinerary createItinerary(Itinerary itinerary);
    void cancelItinerary(Itinerary itinerary);
    Itinerary updateItinerary(Itinerary itinerary);
    List<Itinerary> getItineraries(Customer customer);

}
