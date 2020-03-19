package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.exception.FlightNotExistException;
import com.ra.course.ams.airline.manag.system.exception.ScheduleNotExistException;
import com.ra.course.ams.airline.manag.system.repository.flight.CustomScheduleRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightInstanceRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.WeeklyScheduleRepository;
import com.ra.course.ams.airline.manag.system.service.InformationService;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class InformationServiceImpl implements InformationService {

    transient public String flightNumerErrMes = "FlightNumber for search cannot be null, empty or blank";
    transient public String airportErMes = "Airport cannot be null, empty or blank";

    transient private WeeklyScheduleRepository weeklySchedRepo;
    transient private CustomScheduleRepository customSchedRepo;
    transient private FlightInstanceRepository flightInstRepo;
    transient private FlightRepository flightRepository;

    @Override
    public WeeklySchedule checkFlightWeeklySchedule(final String flightNumber) {
        if (flightNumber == null || flightNumber.isBlank()) {
            throw new IllegalArgumentException(flightNumerErrMes);
        }

        return weeklySchedRepo.getInstances().stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElseThrow(() -> new ScheduleNotExistException("There are no WeeklySchedule with this number"));
    }

    @Override
    public CustomSchedule checkFlightCustomSchedule(final String flightNumber) {
        if (flightNumber == null || flightNumber.isBlank()) {
            throw new IllegalArgumentException(flightNumerErrMes);
        }

        return customSchedRepo.getInstances().stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElseThrow(() -> new ScheduleNotExistException("There are no CustomSchedule with this number"));
    }

    @Override
    public List<FlightSeat> checkAvailableSeats(final FlightInstance flightInstance) {

        return getNecessaryFlightInstanse(flightInstance).getSeats();
    }

    @Override
    public Time checkDepartureTime(final FlightInstance flightInstance) {

        return getNecessaryFlightInstanse(flightInstance).getDepartureTime();
    }

    @Override
    public Time checkArrivalTime(final FlightInstance flightInstance) {

        return getNecessaryFlightInstanse(flightInstance).getArrivalTime();
    }

    @Override
    public List<Flight> searchFlightByDate(final Date date) {
        if (Optional.ofNullable(date).isPresent()) {

            return flightRepository.getInstances().stream()
                    .filter(i -> date.equals(i.getDate()))
                    .collect(Collectors.toList());

        }
        throw new IllegalArgumentException("Date cannot be null, empty or blank");
    }

    @Override
    public List<Flight> searchFlightByDepartureAirport(final Airport airport) {

        if (Optional.ofNullable(airport).isPresent()) {

            return flightRepository.getInstances().stream()
                    .filter(i -> airport.equals(i.getArrival()))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException(airportErMes);
    }

    @Override
    public List<Flight> searchFlightByArrivalAirport(final Airport airport) {
        if (Optional.ofNullable(airport).isPresent()) {

            return flightRepository.getInstances().stream()
                    .filter(i -> airport.equals(i.getDeparture()))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException(airportErMes);
    }

    private FlightInstance getNecessaryFlightInstanse(final FlightInstance flightInstance) {
        if (Optional.ofNullable(flightInstance).isPresent()) {

            return flightInstRepo.getInstances().stream()
                    .filter(i -> flightInstance.equals(i))
                    .findAny()
                    .orElseThrow(() -> new FlightNotExistException("There are no neccessory Flight instanse : ("));
        }
        throw new IllegalArgumentException("FlightInstance cannot be null, empty or blank");
    }

    public void setShedRepos(final WeeklyScheduleRepository weeklySchedRepo, final CustomScheduleRepository customSchedRepo) {
        this.weeklySchedRepo = weeklySchedRepo;
        this.customSchedRepo = customSchedRepo;
    }

    public void setFligRepos(final FlightInstanceRepository flightInstRepo,
                             final FlightRepository flightRepository) {
        this.flightInstRepo = flightInstRepo;
        this.flightRepository = flightRepository;
    }
}

