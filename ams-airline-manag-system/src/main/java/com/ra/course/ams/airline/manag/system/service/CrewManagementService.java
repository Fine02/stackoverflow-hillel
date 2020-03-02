package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Person;

public interface CrewManagementService{

    Crew addFlightInstance(Crew crew, FlightInstance flightInstance);
    Crew removeFlightInstance(Crew crew, FlightInstance flightInstance);

}
