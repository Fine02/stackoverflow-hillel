package com.ra.course.ams.airline.manag.system.entity.person;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.List;

public class Crew extends Person {
    private List<FlightInstance> flightInstances;

    public Crew() {
        super();
    }

    public Crew(List<FlightInstance> flightInstances) {
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
