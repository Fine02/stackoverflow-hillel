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
    public Optional<WeeklySchedule> checkFlightWeeklySchedule(final String flightNumber) {

        return Optional.of(weeklySchedRepo.getInstances().stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElseThrow(() -> new ScheduleNotExistException("There are no WeeklySchedule with this number")));
    }

    @Override
    public Optional<CustomSchedule> checkFlightCustomSchedule(final String flightNumber) {

        return Optional.of(customSchedRepo.getInstances().stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElseThrow(() -> new ScheduleNotExistException("There are no CustomSchedule with this number")));
    }

    @Override
    public Optional<List<FlightSeat>> checkAvailableSeats(final FlightInstance flightInstance) {

        return Optional.of(getNecessaryFlightInstanse(flightInstance).get().getSeats());
    }

    @Override
    public Optional<Time> checkDepartureTime(final FlightInstance flightInstance) {

        return Optional.of(getNecessaryFlightInstanse(flightInstance).get().getDepartureTime());
    }

    @Override
    public Optional<Time> checkArrivalTime(final FlightInstance flightInstance) {

        return Optional.of(getNecessaryFlightInstanse(flightInstance).get().getArrivalTime());
    }

    @Override
    public Optional<List<Flight>> searchFlightByDate(final Date date) {

        return Optional.of(flightRepository.getInstances().stream()
                .filter(i -> date.equals(i.getDate()))
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Flight>> searchFlightByDepartureAirport(final Airport airport) {

        return Optional.of(flightRepository.getInstances().stream()
                .filter(i -> airport.equals(i.getArrival()))
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Flight>> searchFlightByArrivalAirport(final Airport airport) {

        return Optional.of(flightRepository.getInstances().stream()
                .filter(i -> airport.equals(i.getDeparture()))
                .collect(Collectors.toList()));
    }

    private Optional <FlightInstance> getNecessaryFlightInstanse(final FlightInstance flightInstance) {

            return Optional.of(flightInstRepo.getInstances().stream()
                    .filter(i -> flightInstance.equals(i))
                    .findAny()
                    .orElseThrow(() -> new FlightNotExistException("There are no neccessory Flight instanse : (")));
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

