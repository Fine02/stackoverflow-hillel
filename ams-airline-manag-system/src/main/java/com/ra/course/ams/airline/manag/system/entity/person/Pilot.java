package com.ra.course.ams.airline.manag.system.entity.person;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.List;

public class Pilot extends Person {

    private List<FlightInstance> flightInstances;

    public Pilot() {
        super();
    }

    public Pilot(List<FlightInstance> flightInstances) {
        super();
        this.flightInstances = flightInstances;
    }

    public List<FlightInstance> getFlightInstances() {
        return flightInstances;
    }

    public void setFlightInstances(List<FlightInstance> flightInstances) {
        this.flightInstances = flightInstances;
    }
}
