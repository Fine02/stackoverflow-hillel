package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Itinerary;
import com.ra.course.ams.airline.manag.system.entity.person.Customer;

import java.util.List;

public interface ItineraryService {

    Itinerary createItinerary(Itinerary itinerary, String accounId);

    void cancelItinerary(Itinerary itinerary, String accounId);

    Itinerary updateItinerary(Itinerary itinerary, String accounId);

    List<Itinerary> getItineraries(Customer customer, String accounId);

}
