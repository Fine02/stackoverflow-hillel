package com.ra.course.ams.airline.manag.system.entity;

import java.util.Date;
import java.util.List;

public class Passenger {

    private String name;
    private String passportNumber;
    private Date dateOdBirth;
    private List<Itinerary> itineraries;

    public Passenger() {
    }

    public Passenger(String name, String passportNumber) {
        this.name = name;
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getDateOdBirth() {
        return dateOdBirth;
    }

    public void setDateOdBirth(Date dateOdBirth) {
        this.dateOdBirth = dateOdBirth;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Passenger{");
        sb.append("\"name\": \"").append(name).append('"');
        sb.append(", \"passportNumber\": \"").append(passportNumber).append('"');
        sb.append(", \"dateOdBirth\": \"").append(dateOdBirth).append('"');
        sb.append(", \"itineraries\":").append(itineraries);
        sb.append('}');
        return sb.toString();
    }
}
