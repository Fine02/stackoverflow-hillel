package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Itinerary;
import com.ra.course.ams.airline.manag.system.entity.person.Customer;

import java.util.List;
import java.util.Optional;

public interface ItineraryService {

    Optional <Itinerary> createItinerary(Itinerary itinerary);

    void cancelItinerary(Itinerary itinerary);

    Optional <Itinerary> updateItinerary(Itinerary itinerary);

    Optional <List<Itinerary>> getItineraries(Customer customer);
}
