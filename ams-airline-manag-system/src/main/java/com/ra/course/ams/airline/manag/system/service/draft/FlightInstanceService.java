package com.ra.course.ams.airline.manag.system.service.draft;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

public interface FlightInstanceService {

    FlightInstance add(FlightInstance flightInstance);
    void updateStatus(FlightInstance flightInstance, FlightStatus status);
    boolean cancel(FlightInstance flightInstance);
    boolean assignPilot(FlightInstance flightInstance, Pilot pilot);
    boolean assignCrew(FlightInstance flightInstance, Crew crew);

}
