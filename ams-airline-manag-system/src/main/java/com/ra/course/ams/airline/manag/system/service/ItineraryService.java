package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Itinerary;
import com.ra.course.ams.airline.manag.system.entity.person.Customer;

import java.util.List;

public interface ItineraryService {

    Itinerary createItinerary(Itinerary itinerary);
    void cancelItinerary(Itinerary itinerary);
    Itinerary updateItinerary(Itinerary itinerary);
    List<Itinerary> getItineraries(Customer customer);
}
