package com.ra.course.ams.airline.manag.system.entity.person;


import com.ra.course.ams.airline.manag.system.entity.Itinerary;

import java.util.List;

public class Customer extends Person {

    private String frequentlyFlyerNumber;
    private List<Itinerary> itineraries;

    public Customer() {
    }

    public String getFrequentlyFlyerNumber() {
        return frequentlyFlyerNumber;
    }

    public void setFrequentlyFlyerNumber(String frequentlyFlyerNumber) {
        this.frequentlyFlyerNumber = frequentlyFlyerNumber;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

}
